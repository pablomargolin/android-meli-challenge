package com.margo.news_feed.data.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.network.safeApiCall
import com.margo.news_feed.data.mapper.toDomain
import com.margo.news_feed.data.remote.NewsFeedApi
import com.margo.news_feed.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsFeedApi
): NewsRepository {
    override suspend fun getNews(query: String?): Result<List<Article>> {
        val networkResult = safeApiCall { api.getArticles(query = query) }

        return when (networkResult) {
            is Result.Success -> {
                val articlesDomain = networkResult.data.results.map { it.toDomain() }
                Result.Success(articlesDomain)
            }
            is Result.Error -> {
                Result.Error(networkResult.errorType, networkResult.exception)
            }
        }
    }
}