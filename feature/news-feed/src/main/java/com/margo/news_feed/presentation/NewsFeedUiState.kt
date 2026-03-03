package com.margo.news_feed.presentation

import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article

/**
 * Represents the various states of the News Feed UI.
 */
sealed interface NewsFeedUiState {
    /**
     * Represents a successful state where data is available to be displayed.
     *
     * @property articles The list of articles to display.
     * @property isPaginating True if the app is currently fetching the next page of articles.
     * @property isLastPage True if there are no more articles to fetch.
     * @property paginationError Holds an [ErrorType] if a silent pagination request failed.
     */
    data class Success(
        val articles: List<Article>,
        val isPaginating: Boolean = false,
        val isLastPage: Boolean = false,
        val paginationError: ErrorType? = null
    ) : NewsFeedUiState

    /**
     * Represents a hard error state where the screen cannot load any initial data.
     *
     * @property errorType The specific type of error encountered.
     */
    data class Error(val errorType: ErrorType) : NewsFeedUiState

    /**
     * Represents a loading state, typically used during the initial fetch.
     */
    object Loading : NewsFeedUiState
}