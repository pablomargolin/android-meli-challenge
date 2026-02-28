package com.margo.news_feed.data.remote

import com.margo.news_feed.data.remote.dto.NewsFeedResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedApi {

    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("search") query: String? = null
    ): Response<NewsFeedResponseDto>
}