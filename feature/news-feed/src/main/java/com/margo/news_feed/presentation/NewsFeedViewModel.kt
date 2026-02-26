package com.margo.news_feed.presentation

import android.util.Log
import com.margo.domain.common.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margo.domain.common.ErrorType
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

                    val errorMessage = when (result.errorType) {
                        ErrorType.NO_INTERNET -> "No hay conexión a internet. Revisa tu wifi o datos."
                        ErrorType.SERVER_ERROR -> "Los servidores espaciales están fallando. Intenta más tarde."
                        ErrorType.NOT_FOUND -> "No pudimos encontrar las noticias solicitadas."
                        ErrorType.UNKNOWN -> "Ocurrió un error inesperado al cargar el feed."
                    }

                    _uiState.value = NewsFeedUiState.Error(errorMessage)
                }
            }
        }
    }
}