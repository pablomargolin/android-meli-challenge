package com.margo.news_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_feed.domain.usecase.GetNewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import timber.log.Timber

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * ViewModel responsible for managing the state and logic of the News Feed screen.
 * It handles fetching paginated news, managing search queries, and retaining state across configuration changes.
 *
 * @property getNewsFeedUseCase The use case used to fetch news data.
 */
@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase
) : ViewModel() {

    data class FeedState(
        val offset: Int = 0,
        val isLastPage: Boolean = false,
        val isPaginating: Boolean = false,
        val articles: List<Article> = emptyList()
    )

    private val _feedState = MutableStateFlow(FeedState())

    private val _uiState = MutableStateFlow<NewsFeedUiState>(NewsFeedUiState.Loading)
    
    /**
     * The observable state of the UI. Represents loading, success (with data), or error states.
     */
    val uiState: StateFlow<NewsFeedUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    
    /**
     * The current search query entered by the user.
     */
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchNews()
    }

    /**
     * Updates the current search query and triggers a new debounced network request.
     *
     * @param query The new search query string.
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            fetchNews(query.takeIf { it.isNotBlank() }, isRefresh = true)
        }
    }

    /**
     * Fetches news articles from the repository.
     *
     * @param query Optional search term to filter results.
     * @param isRefresh If true, clears the current list and resets pagination. If false, fetches the next page.
     */
    fun fetchNews(query: String? = null, isRefresh: Boolean = true) {
        if (isRefresh) {
            _feedState.value = FeedState()
            _uiState.value = NewsFeedUiState.Loading
        } else {
            val currentFeedState = _feedState.value
            if (currentFeedState.isLastPage || currentFeedState.isPaginating) return
            
            _feedState.update { it.copy(isPaginating = true) }
            
            val currentState = _uiState.value
            if (currentState is NewsFeedUiState.Success) {
                _uiState.value = currentState.copy(isPaginating = true)
            }
        }

        viewModelScope.launch {
            val currentOffset = _feedState.value.offset
            when (val result = getNewsFeedUseCase(query, currentOffset)) {

                is Result.Success -> {
                    val newArticles = result.data
                    
                    _feedState.update { currentState ->
                        val uniqueNewArticles = newArticles.filterNot { newArticle ->
                            currentState.articles.any { it.id == newArticle.id }
                        }
                        
                        currentState.copy(
                            isLastPage = newArticles.isEmpty(),
                            articles = currentState.articles + uniqueNewArticles,
                            offset = currentState.offset + newArticles.size,
                            isPaginating = false
                        )
                    }
                    
                    val updatedFeedState = _feedState.value
                    _uiState.value = NewsFeedUiState.Success(
                        articles = updatedFeedState.articles,
                        isPaginating = false,
                        isLastPage = updatedFeedState.isLastPage
                    )
                }

                is Result.Error -> {
                    _feedState.update { it.copy(isPaginating = false) }
                    if (isRefresh) {
                        result.exception?.let { error ->
                            Timber.e(error, "Failed to fetch news")
                        }
                        _uiState.value = NewsFeedUiState.Error(result.errorType)
                    } else {
                        val currentState = _uiState.value
                        if (currentState is NewsFeedUiState.Success) {
                            _uiState.value = currentState.copy(
                                isPaginating = false,
                                paginationError = result.errorType
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Triggers the pagination logic to fetch the next set of articles using the current search query.
     */
    fun loadMore() {
        val query = _searchQuery.value.takeIf { it.isNotBlank() }
        fetchNews(query, isRefresh = false)
    }

    /**
     * Clears any existing pagination error from the UI state, usually called after a Toast is displayed.
     */
    fun clearPaginationError() {
        val currentState = _uiState.value
        if (currentState is NewsFeedUiState.Success) {
            _uiState.value = currentState.copy(paginationError = null)
        }
    }
}
