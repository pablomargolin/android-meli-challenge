package com.margo.news_detail.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_detail.domain.repository.NewsDetailRepository
import com.margo.news_detail.domain.usecase.GetArticleDetailUseCase
import io.mockk.coEvery
import io.mockk.every
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
class NewsDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getArticleDetailUseCase: GetArticleDetailUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<Int>("articleId") } returns 1
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getArticleDetail updates uiState to Success when repository returns Success`() = runTest {
        val article = Article(1, "title", emptyList(), null, null, null, "summary", "published")
        coEvery { getArticleDetailUseCase(1) } returns Result.Success(article)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            assertEquals(NewsDetailUiState.Success(article), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error NO_INTERNET when repository returns NO_INTERNET`() = runTest {
        coEvery { getArticleDetailUseCase(1) } returns Result.Error(ErrorType.NO_INTERNET)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            assertEquals(NewsDetailUiState.Error(ErrorType.NO_INTERNET), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error NOT_FOUND when repository returns NOT_FOUND`() = runTest {
        coEvery { getArticleDetailUseCase(1) } returns Result.Error(ErrorType.NOT_FOUND)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            assertEquals(NewsDetailUiState.Error(ErrorType.NOT_FOUND), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error SERVER_ERROR when repository returns SERVER_ERROR`() = runTest {
        coEvery { getArticleDetailUseCase(1) } returns Result.Error(ErrorType.SERVER_ERROR)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            assertEquals(NewsDetailUiState.Error(ErrorType.SERVER_ERROR), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error UNKNOWN when repository returns UNKNOWN`() = runTest {
        coEvery { getArticleDetailUseCase(1) } returns Result.Error(ErrorType.UNKNOWN)

        viewModel = NewsDetailViewModel(getArticleDetailUseCase, savedStateHandle)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            assertEquals(NewsDetailUiState.Error(ErrorType.UNKNOWN), awaitItem())
        }
    }
}
