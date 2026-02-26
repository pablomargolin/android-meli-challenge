package com.margo.news_detail.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_detail.domain.repository.NewsDetailRepository
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
    private val repository: NewsDetailRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<Int>("articleId") } returns 1
        viewModel = NewsDetailViewModel(repository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getArticleDetail updates uiState to Success when repository returns Success`() = runTest {
        val article = Article(1, "title", null, null, null, null, null, null)
        coEvery { repository.getArticleById(1) } returns Result.Success(article)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            
            viewModel.getArticleDetail()

            assertEquals(NewsDetailUiState.Success(article), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error NO_INTERNET when repository returns NO_INTERNET`() = runTest {
        coEvery { repository.getArticleById(1) } returns Result.Error(ErrorType.NO_INTERNET)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            
            viewModel.getArticleDetail()
            
            assertEquals(NewsDetailUiState.Error("No hay conexión a internet. Revisa tu wifi o datos."), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error NOT_FOUND when repository returns NOT_FOUND`() = runTest {
        coEvery { repository.getArticleById(1) } returns Result.Error(ErrorType.NOT_FOUND)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            
            viewModel.getArticleDetail()
            
            assertEquals(NewsDetailUiState.Error("No pudimos encontrar el articulo."), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error SERVER_ERROR when repository returns SERVER_ERROR`() = runTest {
        coEvery { repository.getArticleById(1) } returns Result.Error(ErrorType.SERVER_ERROR)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            
            viewModel.getArticleDetail()
            
            assertEquals(NewsDetailUiState.Error("Los servidores espaciales están fallando. Intenta más tarde."), awaitItem())
        }
    }

    @Test
    fun `getArticleDetail updates uiState to Error UNKNOWN when repository returns UNKNOWN`() = runTest {
        coEvery { repository.getArticleById(1) } returns Result.Error(ErrorType.UNKNOWN)

        viewModel.uiState.test {
            assertEquals(NewsDetailUiState.Loading, awaitItem())
            
            viewModel.getArticleDetail()
            
            assertEquals(NewsDetailUiState.Error("Ocurrió un error inesperado al cargar el articulo."), awaitItem())
        }
    }
}
