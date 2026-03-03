package com.margo.news_feed.presentation

import app.cash.turbine.test
import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.domain.utils.empty
import com.margo.news_feed.domain.usecase.GetNewsFeedUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getNewsFeedUseCase: GetNewsFeedUseCase = mockk()
    
    private lateinit var viewModel: NewsFeedViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews updates uiState to Success when repository returns Success`() = runTest {
        val mockArticles = listOf(
            Article(1, "title", emptyList(), "url", "image", "site", "summary", "published")
        )
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Success(mockArticles)
        
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Success(mockArticles, isPaginating = false, isLastPage = false), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error NO_INTERNET when repository returns NO_INTERNET`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Error(ErrorType.NO_INTERNET)
        
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.NO_INTERNET), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error SERVER_ERROR when repository returns SERVER_ERROR`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Error(ErrorType.SERVER_ERROR)
        
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.SERVER_ERROR), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error NOT_FOUND when repository returns NOT_FOUND`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Error(ErrorType.NOT_FOUND)
        
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.NOT_FOUND), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error UNKNOWN when repository returns UNKNOWN`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Error(ErrorType.UNKNOWN)
        
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.UNKNOWN), awaitItem())
        }
    }

    @Test
    fun `updateSearchQuery should update the search query and fetch`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Success(emptyList())

        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        viewModel.updateSearchQuery("query")

        assertEquals("query", viewModel.searchQuery.value)

        advanceUntilIdle()

        coVerify { getNewsFeedUseCase("query", 0) }
    }

    @Test
    fun `updateSearchQuery should not fetch with query if it is blank`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Success(emptyList())

        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        viewModel.updateSearchQuery(String.empty())

        assertEquals(String.empty(), viewModel.searchQuery.value)

        advanceUntilIdle()

        coVerify { getNewsFeedUseCase(null, 0) }
    }

    @Test
    fun `updateSearchQuery with rapid changes should trigger only one network call after debounce`() = runTest {
        coEvery { getNewsFeedUseCase(any(), any()) } returns Result.Success(emptyList())
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        viewModel.updateSearchQuery("M")
        viewModel.updateSearchQuery("Me")
        viewModel.updateSearchQuery("Mel")
        viewModel.updateSearchQuery("Meli")

        advanceUntilIdle()

        coVerify(exactly = 1) { getNewsFeedUseCase("Meli", 0) }
        coVerify(exactly = 0) { getNewsFeedUseCase("M", 0) }
        coVerify(exactly = 0) { getNewsFeedUseCase("Me", 0) }
        coVerify(exactly = 0) { getNewsFeedUseCase("Mel", 0) }
    }

    @Test
    fun `fetchNews should filter duplicate articles by ID`() = runTest {
        val article1 = Article(1, "title1", emptyList(), null, null, null, "summary1", "published1")
        val article1Duplicate = Article(1, "title1 duplicate", emptyList(), null, null, null, "summary1", "published1")
        
        coEvery { getNewsFeedUseCase(null, 0) } returns Result.Success(listOf(article1))
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        coEvery { getNewsFeedUseCase(null, 1) } returns Result.Success(listOf(article1Duplicate))
        
        viewModel.loadMore()
        advanceUntilIdle()

        val state = viewModel.uiState.value as NewsFeedUiState.Success
        assertEquals(1, state.articles.size)
        assertEquals("title1", state.articles[0].title)
    }

    @Test
    fun `loadMore error should set paginationError without losing existing articles`() = runTest {
        val initialArticles = listOf(
            Article(1, "title1", emptyList(), null, null, null, "summary1", "published1")
        )
        coEvery { getNewsFeedUseCase(null, 0) } returns Result.Success(initialArticles)
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        coEvery { getNewsFeedUseCase(null, 1) } returns Result.Error(ErrorType.NO_INTERNET)

        viewModel.loadMore()
        advanceUntilIdle()

        val state = viewModel.uiState.value as NewsFeedUiState.Success
        assertEquals(initialArticles, state.articles)
        assertEquals(ErrorType.NO_INTERNET, state.paginationError)
    }

    @Test
    fun `clearPaginationError should remove error from state`() = runTest {
        val initialArticles = listOf(
            Article(1, "title1", emptyList(), null, null, null, "summary1", "published1")
        )
        coEvery { getNewsFeedUseCase(null, 0) } returns Result.Success(initialArticles)
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        coEvery { getNewsFeedUseCase(null, 1) } returns Result.Error(ErrorType.SERVER_ERROR)
        viewModel.loadMore()
        advanceUntilIdle()

        viewModel.clearPaginationError()
        
        val state = viewModel.uiState.value as NewsFeedUiState.Success
        assertEquals(null, state.paginationError)
    }

    @Test
    fun `loadMore should append new articles to existing list`() = runTest {
        val initialArticles = listOf(
            Article(1, "title1", emptyList(), "url1", "image1", "site1", "summary1", "published1")
        )
        val nextArticles = listOf(
            Article(2, "title2", emptyList(), "url2", "image2", "site2", "summary2", "published2")
        )

        coEvery { getNewsFeedUseCase(null, 0) } returns Result.Success(initialArticles)
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        coEvery { getNewsFeedUseCase(null, 1) } returns Result.Success(nextArticles)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Success(initialArticles, isPaginating = false, isLastPage = false), awaitItem())
            
            viewModel.loadMore()
            
            assertEquals(NewsFeedUiState.Success(initialArticles, isPaginating = true, isLastPage = false), awaitItem())
            assertEquals(NewsFeedUiState.Success(initialArticles + nextArticles, isPaginating = false, isLastPage = false), awaitItem())
        }
        
        coVerify { getNewsFeedUseCase(null, 0) }
        coVerify { getNewsFeedUseCase(null, 1) }
    }

    @Test
    fun `loadMore should set isLastPage to true when empty list is returned`() = runTest {
        val initialArticles = listOf(
            Article(1, "title1", emptyList(), "url1", "image1", "site1", "summary1", "published1")
        )
        
        coEvery { getNewsFeedUseCase(null, 0) } returns Result.Success(initialArticles)
        viewModel = NewsFeedViewModel(getNewsFeedUseCase)
        advanceUntilIdle()

        coEvery { getNewsFeedUseCase(null, 1) } returns Result.Success(emptyList())

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Success(initialArticles, isPaginating = false, isLastPage = false), awaitItem())
            
            viewModel.loadMore()
            
            assertEquals(NewsFeedUiState.Success(initialArticles, isPaginating = true, isLastPage = false), awaitItem())
            assertEquals(NewsFeedUiState.Success(initialArticles, isPaginating = false, isLastPage = true), awaitItem())
        }
    }
}
