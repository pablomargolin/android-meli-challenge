package com.margo.news_feed.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article

sealed interface NewsFeedUiState {
    data class Success(
        val articles: List<Article>,
        val isPaginating: Boolean = false,
        val isLastPage: Boolean = false,
        val paginationError: ErrorType? = null
    ) : NewsFeedUiState
    data class Error(val errorType: ErrorType) : NewsFeedUiState
    object Loading : NewsFeedUiState
}