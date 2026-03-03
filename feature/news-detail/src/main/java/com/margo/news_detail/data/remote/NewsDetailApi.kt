package com.margo.news_detail.data.remote

import com.margo.news_detail.data.remote.dto.ArticleDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface for fetching individual article details from the Space Flight News API.
 */
interface NewsDetailApi {
    /**
     * Retrieves the details of a specific article by its ID.
     *
     * @param id The unique identifier of the article.
     * @return A Retrofit [Response] containing the [ArticleDetailDto].
     */
    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDetailDto>
}