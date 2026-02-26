package com.margo.news_detail.data.remote.dto

import com.squareup.moshi.Json

data class ArticleDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "published_at") val publishedAt: String?
)
