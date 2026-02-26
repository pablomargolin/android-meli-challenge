package com.margo.news_detail.presentation

import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsDetailRepository: NewsDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsDetailUiState>(NewsDetailUiState.Loading)
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()

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
                        Log.e("NewsFeedViewModel", "Fallo al obtener noticias: ${error.message}", error)
                    }

                    val errorMessage = when (result.errorType) {
                        ErrorType.NO_INTERNET -> "No hay conexión a internet. Revisa tu wifi o datos."
                        ErrorType.SERVER_ERROR -> "Los servidores espaciales están fallando. Intenta más tarde."
                        ErrorType.NOT_FOUND -> "No pudimos encontrar el articulo."
                        ErrorType.UNKNOWN -> "Ocurrió un error inesperado al cargar el articulo."
                    }

                    _uiState.value = NewsDetailUiState.Error(errorMessage)
                }
            }
        }
    }

}