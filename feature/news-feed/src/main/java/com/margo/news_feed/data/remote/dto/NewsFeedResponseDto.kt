package com.margo.news_feed.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data Transfer Object (DTO) representing the paginated response wrapper from the news feed API.
 */
data class NewsFeedResponseDto(
    @Json(name = "results")
    val results: List<ArticleDto>
)
