package com.margo.news_feed.domain.usecase

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_feed.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNewsFeedUseCaseTest {

    private val repository: NewsRepository = mockk()
    private val useCase = GetNewsFeedUseCase(repository)

    @Test
    fun `invoke should call repository getNews and return result`() = runTest {
        val articles = listOf(
            Article(1, "title", emptyList(), "url", "image", "site", "summary", "published")
        )
        val expectedResult = Result.Success(articles)
        
        coEvery { repository.getNews("query", 0) } returns expectedResult

        val actualResult = useCase("query", 0)

        assertEquals(expectedResult, actualResult)
    }
}
