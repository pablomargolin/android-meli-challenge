package com.margo.domain.model

/**
 * Domain representation of a Space Flight News Article.
 *
 * @property id Unique identifier of the article.
 * @property title The headline or title of the article.
 * @property authors A list of [Author]s who wrote the article.
 * @property url The original URL of the article.
 * @property imageUrl The URL of the article's featured image.
 * @property newsSite The name of the site that published the article.
 * @property summary A brief summary or snippet of the article's content.
 * @property publishedAt The formatted publication date string.
 */
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
