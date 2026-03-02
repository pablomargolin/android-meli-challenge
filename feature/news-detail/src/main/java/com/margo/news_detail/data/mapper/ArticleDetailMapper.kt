package com.margo.news_detail.data.mapper

import com.margo.domain.model.Article
import com.margo.news_detail.data.remote.dto.ArticleDetailDto

/**
 * Maps an [ArticleDetailDto] from the data layer into an [Article] for the domain layer.
 */
fun ArticleDetailDto.toDomain(): Article {
    return Article(
        id = this.id,
        title = this.title,
        summary = this.summary,
        imageUrl = this.imageUrl ?: "",
        publishedAt = this.publishedAt ?: "",
        authors = authors?.map { it.toDomain() },
        newsSite = this.newsSite,
        url = this.url
    )
}