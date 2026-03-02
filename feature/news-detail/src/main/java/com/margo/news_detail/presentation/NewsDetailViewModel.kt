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

/**
 * ViewModel responsible for managing the state and logic of the News Detail screen.
 * It retrieves the article ID from the navigation arguments and fetches its full details.
 *
 * @property newsDetailRepository The repository used to fetch specific article details.
 * @property savedStateHandle Handle to retrieve navigation arguments like the article ID.
 */
@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsDetailRepository: NewsDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsDetailUiState>(NewsDetailUiState.Loading)
    
    /**
     * The observable state of the UI. Represents loading, success (with article details), or error states.
     */
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()

    init {
        getArticleDetail()
    }
    
    /**
     * Fetches the details of the article using the ID passed via navigation.
     * Updates the [uiState] based on the result of the network call.
     */
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