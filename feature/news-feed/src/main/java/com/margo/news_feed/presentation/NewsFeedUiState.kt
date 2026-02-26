package com.margo.news_feed.presentation

import com.margo.domain.model.Article

sealed interface NewsFeedUiState {
    data class Success(val articles: List<Article>) : NewsFeedUiState
    data class Error(val message: String) : NewsFeedUiState
    object Loading : NewsFeedUiState
}