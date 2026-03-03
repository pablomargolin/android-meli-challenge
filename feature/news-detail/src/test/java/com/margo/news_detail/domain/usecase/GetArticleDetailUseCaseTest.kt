package com.margo.news_detail.domain.usecase

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_detail.domain.repository.NewsDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetArticleDetailUseCaseTest {

    private val repository: NewsDetailRepository = mockk()
    private val useCase = GetArticleDetailUseCase(repository)

    @Test
    fun `invoke should call repository getArticleById and return result`() = runTest {
        val article = Article(1, "title", emptyList(), "url", "image", "site", "summary", "published")
        val expectedResult = Result.Success(article)
        
        coEvery { repository.getArticleById(1) } returns expectedResult

        val actualResult = useCase(1)

        assertEquals(expectedResult, actualResult)
    }
}
