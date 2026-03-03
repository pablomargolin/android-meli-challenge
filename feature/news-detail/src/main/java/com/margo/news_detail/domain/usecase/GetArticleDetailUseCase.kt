package com.margo.news_detail.domain.usecase

import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_detail.domain.repository.NewsDetailRepository
import javax.inject.Inject

/**
 * Use case to fetch the full details of a specific article.
 *
 * @property repository The repository that provides article details.
 */
class GetArticleDetailUseCase @Inject constructor(
    private val repository: NewsDetailRepository
) {
    /**
     * Executes the use case.
     *
     * @param id The unique identifier of the article.
     * @return A [Result] containing the article details or an error.
     */
    suspend operator fun invoke(id: Int): Result<Article> {
        return repository.getArticleById(id)
    }
}
