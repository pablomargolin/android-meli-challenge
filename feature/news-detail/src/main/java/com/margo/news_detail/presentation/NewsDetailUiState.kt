package com.margo.news_detail.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article

/**
 * Represents the various states of the News Detail UI.
 */
sealed interface NewsDetailUiState {
    /**
     * Represents a successful state where the article details were retrieved.
     *
     * @property article The domain [Article] containing full details.
     */
    data class Success(val article: Article) : NewsDetailUiState

    /**
     * Represents a failed state.
     *
     * @property errorType The specific type of error encountered.
     */
    data class Error(val errorType: ErrorType) : NewsDetailUiState

    /**
     * Represents the initial loading state while fetching article details.
     */
    object Loading : NewsDetailUiState
}