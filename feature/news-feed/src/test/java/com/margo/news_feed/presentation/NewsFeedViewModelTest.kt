package com.margo.news_feed.presentation

import app.cash.turbine.test
import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.domain.utils.empty
import com.margo.news_feed.domain.repository.NewsRepository
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
    private val repository: NewsRepository = mockk()
    
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
            Article(1, "title", emptyList(), "url", "image", "site", "summary", "´published")
        )
        coEvery { repository.getNews(any()) } returns Result.Success(mockArticles)
        
        viewModel = NewsFeedViewModel(repository)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Success(mockArticles), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error NO_INTERNET when repository returns NO_INTERNET`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Error(ErrorType.NO_INTERNET)
        
        viewModel = NewsFeedViewModel(repository)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.NO_INTERNET), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error SERVER_ERROR when repository returns SERVER_ERROR`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Error(ErrorType.SERVER_ERROR)
        
        viewModel = NewsFeedViewModel(repository)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.SERVER_ERROR), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error NOT_FOUND when repository returns NOT_FOUND`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Error(ErrorType.NOT_FOUND)
        
        viewModel = NewsFeedViewModel(repository)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.NOT_FOUND), awaitItem())
        }
    }

    @Test
    fun `fetchNews updates uiState to Error UNKNOWN when repository returns UNKNOWN`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Error(ErrorType.UNKNOWN)
        
        viewModel = NewsFeedViewModel(repository)

        viewModel.uiState.test {
            assertEquals(NewsFeedUiState.Loading, awaitItem())
            assertEquals(NewsFeedUiState.Error(ErrorType.UNKNOWN), awaitItem())
        }
    }

    @Test
    fun `updateSearchQuery should update the search query and fetch`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Success(emptyList())

        viewModel = NewsFeedViewModel(repository)
        viewModel.updateSearchQuery("query")

        assertEquals("query", viewModel.searchQuery.value)

        advanceUntilIdle()

        coVerify { repository.getNews("query") }
    }

    @Test
    fun `updateSearchQuery should not fetch with query if it is blank`() = runTest {
        coEvery { repository.getNews(any()) } returns Result.Success(emptyList())

        viewModel = NewsFeedViewModel(repository)
        viewModel.updateSearchQuery(String.empty())

        assertEquals(String.empty(), viewModel.searchQuery.value)

        advanceUntilIdle()

        coVerify { repository.getNews(null) }
    }
}