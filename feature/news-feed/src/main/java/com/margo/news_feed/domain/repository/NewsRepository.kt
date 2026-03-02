package com.margo.news_feed.domain.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article

interface NewsRepository {
    suspend fun getNews(query: String? = null, offset: Int = 0): Result<List<Article>>
}