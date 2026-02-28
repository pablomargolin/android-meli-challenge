package com.margo.news_feed.presentation

import android.util.Log
import com.margo.domain.common.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margo.news_feed.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    val uiState: StateFlow<NewsFeedUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }
    fun fetchNews() {
        _uiState.value = NewsFeedUiState.Loading

        viewModelScope.launch {
            when (val result = newsRepository.getNews()) {

                is Result.Success -> {
                    _uiState.value = NewsFeedUiState.Success(result.data)
                }

                is Result.Error -> {
                    result.exception?.let { error ->
                        Log.e("NewsFeedViewModel", "Fallo al obtener noticias: ${error.message}", error)
                    }

                    _uiState.value = NewsFeedUiState.Error(result.errorType)
                }
            }
        }
    }
}