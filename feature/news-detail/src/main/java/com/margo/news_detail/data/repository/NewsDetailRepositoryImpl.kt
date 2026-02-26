package com.margo.news_detail.data.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.network.safeApiCall
import com.margo.news_detail.data.mapper.toDomain
import com.margo.news_detail.data.remote.NewsDetailApi
import com.margo.news_detail.domain.repository.NewsDetailRepository
import javax.inject.Inject

class NewsDetailRepositoryImpl @Inject constructor(
    private val api: NewsDetailApi
): NewsDetailRepository {
    override suspend fun getArticleById(id: Int): Result<Article> {
        val networkResult = safeApiCall { api.getArticleById(id) }

        return when (networkResult) {
            is Result.Success -> {
                Result.Success(networkResult.data.toDomain())
            }
            is Result.Error -> {
                Result.Error(networkResult.errorType, networkResult.exception)
            }
        }
    }
}