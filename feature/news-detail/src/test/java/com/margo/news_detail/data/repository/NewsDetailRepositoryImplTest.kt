package com.margo.news_detail.data.repository

import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.news_detail.data.remote.NewsDetailApi
import com.margo.news_detail.data.remote.dto.ArticleDetailDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class NewsDetailRepositoryImplTest {

    private val api: NewsDetailApi = mockk()
    private val repository = NewsDetailRepositoryImpl(api)

    @Test
    fun `getArticleById returns Success when api returns successful response`() = runTest {
        val dto = ArticleDetailDto(
            id = 1,
            title = "title",
            authors = null,
            summary = "summary",
            imageUrl = "url",
            url = null,
            newsSite = null,
            publishedAt = "published"
        )
        coEvery { api.getArticleById(1) } returns Response.success(dto)

        val result = repository.getArticleById(1)

        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.id)
        assertEquals("title", data.title)
    }

    @Test
    fun `getArticleById returns Error when api call fails with exception`() = runTest {
        coEvery { api.getArticleById(any()) } throws Exception("Network error")

        val result = repository.getArticleById(1)

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(ErrorType.UNKNOWN, errorResult.errorType)
    }

    @Test
    fun `getArticleById returns Error when api call returns error code`() = runTest {
        val errorResponse = Response.error<ArticleDetailDto>(
            404,
            "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
        )
        coEvery { api.getArticleById(any()) } returns errorResponse

        val result = repository.getArticleById(1)

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(ErrorType.NOT_FOUND, errorResult.errorType)
    }
}
