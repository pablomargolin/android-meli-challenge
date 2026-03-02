package com.margo.news_feed.data.mapper

import com.margo.domain.model.Article
import com.margo.news_feed.data.remote.dto.ArticleDto

/**
 * Maps an [ArticleDto] from the data layer into an [Article] for the domain layer.
 */
fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        authors = authors?.map { it.toDomain() },
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt
    )
}