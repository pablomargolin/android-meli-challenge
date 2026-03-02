package com.margo.news_detail.domain.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article

/**
 * Repository interface for retrieving individual article details.
 */
interface NewsDetailRepository {
    /**
     * Fetches the detailed information of a specific article by its ID.
     *
     * @param id The unique identifier of the article.
     * @return A [Result] containing the [Article] or an error.
     */
    suspend fun getArticleById(id: Int): Result<Article>
}