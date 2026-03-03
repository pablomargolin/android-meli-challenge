package com.margo.news_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margo.domain.common.Result
import com.margo.domain.model.Article
import com.margo.news_feed.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import timber.log.Timber

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

/**
 * ViewModel responsible for managing the state and logic of the News Feed screen.
 * It handles fetching paginated news, managing search queries, and retaining state across configuration changes.
 *
 * @property newsRepository The repository used to fetch news data.
 */
@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
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

    private var currentOffset = 0
    private var isLastPage = false
    private var isPaginating = false
    private val currentArticles = mutableListOf<Article>()

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
            currentOffset = 0
            isLastPage = false
            currentArticles.clear()
            _uiState.value = NewsFeedUiState.Loading
        } else {
            if (isLastPage || isPaginating) return
            isPaginating = true
            val currentState = _uiState.value
            if (currentState is NewsFeedUiState.Success) {
                _uiState.value = currentState.copy(isPaginating = true)
            }
        }

        viewModelScope.launch {
            when (val result = newsRepository.getNews(query, currentOffset)) {

                is Result.Success -> {
                    val newArticles = result.data
                    if (newArticles.isEmpty()) {
                        isLastPage = true
                    } else {
                        val uniqueNewArticles = newArticles.filterNot { newArticle ->
                            currentArticles.any { it.id == newArticle.id }
                        }
                        currentArticles.addAll(uniqueNewArticles)
                        currentOffset += newArticles.size
                    }
                    isPaginating = false
                    
                    _uiState.value = NewsFeedUiState.Success(
                        articles = currentArticles.toList(),
                        isPaginating = false,
                        isLastPage = isLastPage
                    )
                }

                is Result.Error -> {
                    isPaginating = false
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
