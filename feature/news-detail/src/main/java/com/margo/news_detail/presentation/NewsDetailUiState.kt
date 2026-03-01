package com.margo.news_detail.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article

sealed interface NewsDetailUiState {
    data class Success(val article: Article) : NewsDetailUiState
    data class Error(val errorType: ErrorType) : NewsDetailUiState
    object Loading : NewsDetailUiState
}