package com.margo.news_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.margo.domain.common.ErrorType
import com.margo.domain.common.Result
import com.margo.news_detail.domain.repository.NewsDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsDetailRepository: NewsDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsDetailUiState>(NewsDetailUiState.Loading)
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()

    init {
        getArticleDetail()
    }
    fun getArticleDetail() {
        val id: Int = checkNotNull(savedStateHandle["articleId"])
        _uiState.value = NewsDetailUiState.Loading

        viewModelScope.launch {
            when (val result = newsDetailRepository.getArticleById(id)) {

                is Result.Success -> {
                    _uiState.value = NewsDetailUiState.Success(result.data)
                }

                is Result.Error -> {
                    result.exception?.let { error ->
                        Timber.e(error, "Failed to fetch article details")
                    }

                    _uiState.value = NewsDetailUiState.Error(result.errorType)
                }
            }
        }
    }

}