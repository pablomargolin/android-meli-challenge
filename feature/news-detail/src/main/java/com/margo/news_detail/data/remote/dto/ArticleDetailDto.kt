package com.margo.news_detail.data.remote.dto

import com.squareup.moshi.Json

data class ArticleDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "authors") val authors: List<AuthorDetailDto>?,
    @Json(name = "summary") val summary: String,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "news_site") val newsSite: String?,
    @Json(name = "published_at") val publishedAt: String?
)
