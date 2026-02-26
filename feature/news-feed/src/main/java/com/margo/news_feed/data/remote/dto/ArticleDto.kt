package com.margo.news_feed.data.remote.dto

import com.squareup.moshi.Json

data class ArticleDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "authors")
    val authors: List<AuthorDto>?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "news_site")
    val newsSite: String?,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "published_at")
    val publishedAt: String?
)
