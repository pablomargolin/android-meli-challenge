package com.margo.news_detail.presentation

import com.margo.domain.model.Article

sealed interface NewsDetailUiState {
    data class Success(val article: Article) : NewsDetailUiState
    data class Error(val message: String) : NewsDetailUiState
    object Loading : NewsDetailUiState
}