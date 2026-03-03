package com.margo.news_feed.data.mapper

import com.margo.domain.model.Article
import com.margo.news_feed.data.remote.dto.ArticleDto

/**
 * Maps an [ArticleDto] from the data layer into an [Article] for the domain layer.
 */
fun ArticleDto.toDomain(): Article {
    return Article(
        id = id ?: throw IllegalStateException("Article ID cannot be null"),
        title = title.orEmpty(),
        authors = authors?.map { it.toDomain() } ?: emptyList(),
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary.orEmpty(),
        publishedAt = publishedAt.orEmpty()
    )
}