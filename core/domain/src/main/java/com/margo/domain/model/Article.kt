package com.margo.domain.model

data class Article(
    val id: Int?,
    val title: String?,
    val authors: List<Author>?,
    val url: String?,
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String?
)
