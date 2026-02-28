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

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    val uiState: StateFlow<NewsFeedUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchNews()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            fetchNews(query.takeIf { it.isNotBlank() })
        }
    }

    fun fetchNews(query: String? = null) {
        _uiState.value = NewsFeedUiState.Loading

        viewModelScope.launch {
            when (val result = newsRepository.getNews(query)) {

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