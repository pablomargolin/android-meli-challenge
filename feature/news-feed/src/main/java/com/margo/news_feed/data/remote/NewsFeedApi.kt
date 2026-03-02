package com.margo.news_feed.data.remote

import com.margo.news_feed.data.remote.dto.NewsFeedResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for the Space Flight News API regarding the news feed.
 */
interface NewsFeedApi {

    /**
     * Retrieves a paginated list of space flight articles.
     *
     * @param limit The maximum number of articles to return per request.
     * @param offset The number of articles to skip.
     * @param query An optional search term to filter the articles.
     * @return A Retrofit [Response] containing the [NewsFeedResponseDto].
     */
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("search") query: String? = null
    ): Response<NewsFeedResponseDto>
}