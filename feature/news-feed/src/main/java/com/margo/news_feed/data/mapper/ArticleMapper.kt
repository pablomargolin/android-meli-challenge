package com.margo.news_feed.data.mapper

import com.margo.domain.model.Article
import com.margo.news_feed.data.remote.dto.ArticleDto

fun ArticleDto.toDomain(): Article {
    return Article(
        title = title,
        authors = authors?.map { it.toDomain() },
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt
    )
}