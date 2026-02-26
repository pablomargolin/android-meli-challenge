package com.margo.news_feed.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_feed.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedViewModelTest {

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsFeedViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = NewsFeedViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews updates uiState to Success when repository returns Success`() = runTest {
        val mockArticles = listOf(
            Article("title", emptyList(), "url", "image", "site", "summary", "´published")
        )
        coEvery { repository.getNews() } returns Result.Success(mockArticles)

        viewModel.fetchNews()
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(NewsFeedUiState.Success(mockArticles), viewModel.uiState.value)
    }

    @Test
    fun `fetchNews updates uiState to Error NO_INTERNET when repository returns NO_INTERNET`() = runTest {
        coEvery { repository.getNews() } returns Result.Error(ErrorType.NO_INTERNET)

        viewModel.fetchNews()
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            NewsFeedUiState.Error("No hay conexión a internet. Revisa tu wifi o datos."),
            viewModel.uiState.value
        )
    }

    @Test
    fun `fetchNews updates uiState to Error SERVER_ERROR when repository returns SERVER_ERROR`() = runTest {
        coEvery { repository.getNews() } returns Result.Error(ErrorType.SERVER_ERROR)

        viewModel.fetchNews()
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            NewsFeedUiState.Error("Los servidores espaciales están fallando. Intenta más tarde."),
            viewModel.uiState.value
        )
    }

    @Test
    fun `fetchNews updates uiState to Error NOT_FOUND when repository returns NOT_FOUND`() = runTest {
        coEvery { repository.getNews() } returns Result.Error(ErrorType.NOT_FOUND)

        viewModel.fetchNews()
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            NewsFeedUiState.Error("No pudimos encontrar las noticias solicitadas."),
            viewModel.uiState.value
        )
    }

    @Test
    fun `fetchNews updates uiState to Error UNKNOWN when repository returns UNKNOWN`() = runTest {
        coEvery { repository.getNews() } returns Result.Error(ErrorType.UNKNOWN)

        viewModel.fetchNews()
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            NewsFeedUiState.Error("Ocurrió un error inesperado al cargar el feed."),
            viewModel.uiState.value
        )
    }
}