package com.margo.news_detail.domain.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article

interface NewsDetailRepository {
    suspend fun getArticleById(id: Int): Result<Article>
}