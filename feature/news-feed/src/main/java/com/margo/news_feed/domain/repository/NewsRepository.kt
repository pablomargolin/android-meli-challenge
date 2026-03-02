package com.margo.news_feed.domain.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article

/**
 * Repository interface for managing news feed data.
 */
interface NewsRepository {
    /**
     * Fetches a paginated list of news articles.
     *
     * @param query Optional search query to filter articles by title or content.
     * @param offset The number of articles to skip for pagination. Defaults to 0.
     * @return A [Result] containing a list of [Article]s or an error.
     */
    suspend fun getNews(query: String? = null, offset: Int = 0): Result<List<Article>>
}