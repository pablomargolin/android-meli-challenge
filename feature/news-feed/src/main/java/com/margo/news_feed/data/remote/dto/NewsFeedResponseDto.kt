package com.margo.news_feed.data.remote.dto

import com.squareup.moshi.Json

data class NewsFeedResponseDto(
    @Json(name = "results")
    val results: List<ArticleDto>
)
