package com.margo.news_detail.data.remote

import com.margo.news_detail.data.remote.dto.ArticleDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsDetailApi {
    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDetailDto>
}