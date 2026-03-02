package com.margo.news_feed.data.repository

import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.news_feed.data.remote.NewsFeedApi
import com.margo.news_feed.data.remote.dto.ArticleDto
import com.margo.news_feed.data.remote.dto.AuthorDto
import com.margo.news_feed.data.remote.dto.NewsFeedResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRepositoryImplTest {

    private lateinit var api: NewsFeedApi
    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        repository = NewsRepositoryImpl(api)
    }

    @Test
    fun `getNews returns Success with mapped articles when api call is successful`() = runTest {
        val mockArticleDto = ArticleDto(
            id = 1,
            title = "title",
            authors = listOf(AuthorDto("name")),
            url = "url",
            imageUrl = "imageUrl",
            newsSite = "newsSite",
            summary = "summary",
            publishedAt = "published"
        )
        val mockResponseDto = NewsFeedResponseDto(results = listOf(mockArticleDto))
        val response = Response.success(mockResponseDto)
        
        coEvery { api.getArticles(limit = any(), offset = any(), query = any()) } returns response

        val result = repository.getNews()

        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(1, successResult.data.size)
        assertEquals("title", successResult.data[0].title)
        assertEquals("name", successResult.data[0].authors?.get(0)?.name)
    }

    @Test
    fun `getNews returns Error when api call fails with exception`() = runTest {
        coEvery { api.getArticles(limit = any(), offset = any(), query = any()) } throws Exception("Network error")

        val result = repository.getNews()

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(ErrorType.UNKNOWN, errorResult.errorType)
    }

    @Test
    fun `getNews returns Error when api call returns error code`() = runTest {
        val errorResponse = Response.error<NewsFeedResponseDto>(
            404,
            "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
        )
        coEvery { api.getArticles(limit = any(), offset = any(), query = any()) } returns errorResponse

        val result = repository.getNews()

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(ErrorType.NOT_FOUND, errorResult.errorType)
    }
}