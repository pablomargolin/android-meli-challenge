package com.margo.news_feed.data.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.network.safeApiCall
import com.margo.network.di.IoDispatcher
import com.margo.news_feed.data.mapper.toDomain
import com.margo.news_feed.data.remote.NewsFeedApi
import com.margo.news_feed.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [NewsRepository] that fetches data from the [NewsFeedApi].
 * It handles the execution of network calls on a designated background dispatcher and maps DTOs to Domain models.
 *
 * @property api The Retrofit API interface for fetching news data.
 * @property ioDispatcher The coroutine dispatcher used for background operations (typically IO).
 */
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsFeedApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NewsRepository {
    override suspend fun getNews(query: String?, offset: Int): Result<List<Article>> = 
        withContext(ioDispatcher) {
            val networkResult = safeApiCall { api.getArticles(query = query, offset = offset) }
            when (networkResult) {
                is Result.Success -> {
                    val articlesDomain = networkResult.data.results.mapNotNull { dto ->
                        runCatching { dto.toDomain() }.getOrNull()
                    }
                    Result.Success(articlesDomain)
                }
                is Result.Error -> {
                    Result.Error(networkResult.errorType, networkResult.exception)
                }
            }
        }
}