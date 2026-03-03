package com.margo.news_feed.domain.usecase

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_feed.domain.repository.NewsRepository
import javax.inject.Inject

/**
 * Use case to fetch a paginated list of news articles.
 *
 * @property repository The repository that provides news data.
 */
class GetNewsFeedUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    /**
     * Executes the use case.
     *
     * @param query Optional search term to filter results.
     * @param offset The pagination offset.
     * @return A [Result] containing the list of articles or an error.
     */
    suspend operator fun invoke(query: String?, offset: Int): Result<List<Article>> {
        return repository.getNews(query, offset)
    }
}
