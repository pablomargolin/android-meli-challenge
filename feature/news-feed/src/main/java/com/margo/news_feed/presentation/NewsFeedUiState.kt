package com.margo.news_feed.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article

sealed interface NewsFeedUiState {
    data class Success(val articles: List<Article>) : NewsFeedUiState
    data class Error(val errorType: ErrorType) : NewsFeedUiState
    object Loading : NewsFeedUiState
}