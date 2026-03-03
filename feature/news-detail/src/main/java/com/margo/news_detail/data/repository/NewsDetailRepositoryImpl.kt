package com.margo.news_detail.data.repository

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.network.di.IoDispatcher
import com.margo.network.safeApiCall
import com.margo.news_detail.data.mapper.toDomain
import com.margo.news_detail.data.remote.NewsDetailApi
import com.margo.news_detail.domain.repository.NewsDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [NewsDetailRepository] that fetches article details from the [NewsDetailApi].
 * It executes network requests on a designated background dispatcher and maps the resulting DTOs to Domain models.
 *
 * @property api The Retrofit API interface for fetching detail data.
 * @property ioDispatcher The coroutine dispatcher used for background IO operations.
 */
class NewsDetailRepositoryImpl @Inject constructor(
    private val api: NewsDetailApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NewsDetailRepository {
    override suspend fun getArticleById(id: Int): Result<Article> = 
        withContext(ioDispatcher) {
            val networkResult = safeApiCall { api.getArticleById(id) }
            when (networkResult) {
                is Result.Success -> {
                    Result.Success(networkResult.data.toDomain())
                }
                is Result.Error -> {
                    Result.Error(networkResult.errorType, networkResult.exception)
                }
            }
        }
}