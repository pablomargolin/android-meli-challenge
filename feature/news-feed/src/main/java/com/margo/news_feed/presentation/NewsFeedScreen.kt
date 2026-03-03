package com.margo.news_feed.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import com.margo.domain.common.ErrorType
import com.margo.shared_ui.components.DesignSearchBar
import com.margo.domain.model.Article
import com.margo.domain.utils.empty
import com.margo.news_feed.R
import com.margo.network.R as NetworkR
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies
import com.margo.shared_ui.components.DesignHighlightImageCard
import com.margo.shared_ui.components.DesignText

/**
 * Entry point route for the News Feed screen.
 * Connects the [NewsFeedViewModel] state and actions to the [NewsFeedScreen] UI.
 *
 * @param onNavigateToDetail Callback triggered with an article ID to navigate to its details.
 * @param viewModel The ViewModel that manages the state of this screen.
 */
@Composable
fun NewsFeedRoute(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: NewsFeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    NewsFeedScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        onQueryChange = viewModel::updateSearchQuery,
        onNavigateToDetail = onNavigateToDetail,
        onLoadMore = viewModel::loadMore,
        onClearPaginationError = viewModel::clearPaginationError,
        onRetry = { viewModel.fetchNews(searchQuery.takeIf { it.isNotBlank() }) }
    )
}

@Composable
internal fun NewsFeedScreen(
    uiState: NewsFeedUiState,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onClearPaginationError: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        containerColor = baseColors.surfaceLightColor.color
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is NewsFeedUiState.Loading -> LoadingScreen()
                is NewsFeedUiState.Success -> {
                    val context = LocalContext.current
                    
                    LaunchedEffect(uiState.paginationError) {
                        uiState.paginationError?.let { errorType ->
                            val errorMessage = when (errorType) {
                                ErrorType.NO_INTERNET -> context.getString(NetworkR.string.error_no_internet)
                                ErrorType.SERVER_ERROR -> context.getString(NetworkR.string.error_server)
                                ErrorType.NOT_FOUND -> context.getString(NetworkR.string.error_not_found)
                                ErrorType.UNKNOWN -> context.getString(NetworkR.string.error_unknown)
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            onClearPaginationError()
                        }
                    }

                    ArticlesContent(
                        articles = uiState.articles,
                        searchQuery = searchQuery,
                        isPaginating = uiState.isPaginating,
                        onQueryChange = onQueryChange,
                        onNavigateToDetail = onNavigateToDetail,
                        onLoadMore = onLoadMore
                    )
                }
                is NewsFeedUiState.Error -> {
                    val errorMessage = when (uiState.errorType) {
                        ErrorType.NO_INTERNET -> stringResource(NetworkR.string.error_no_internet)
                        ErrorType.SERVER_ERROR -> stringResource(NetworkR.string.error_server)
                        ErrorType.NOT_FOUND -> stringResource(NetworkR.string.error_not_found)
                        ErrorType.UNKNOWN -> stringResource(NetworkR.string.error_unknown)
                    }
                    ErrorScreen(
                        message = errorMessage,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(baseSizes.size20.dimension),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DesignText(
                text = message,
                typography = baseTypographies.textBaseNormal,
                textColor = baseColors.blackColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(baseSizes.size20.dimension))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = baseColors.actionColor.color)
            ) {
                DesignText(
                    text = stringResource(R.string.retry_button),
                    typography = baseTypographies.textBaseBold,
                    textColor = baseColors.whiteColor
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("newsScreenLoading"),
            color = baseColors.actionColor.color
        )
    }
}

@Composable
private fun ArticlesContent(
    articles: List<Article>,
    searchQuery: String,
    isPaginating: Boolean,
    onQueryChange: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onLoadMore: () -> Unit
) {
    if (articles.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = baseSizes.size20.dimension)
        ) {
            DesignSearchBar(
                modifier = Modifier.padding(top = baseSizes.size10.dimension),
                query = searchQuery,
                onQueryChange = onQueryChange,
                placeholderText = stringResource(R.string.search_placeholder)
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DesignText(
                    text = stringResource(R.string.empty_news_state),
                    typography = baseTypographies.textBaseNormal,
                    textColor = baseColors.blackColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    val firstArticle = articles.first()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { 
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItemsNumber > 0 && lastVisibleItemIndex >= (totalItemsNumber - 3)
        }
        .distinctUntilChanged()
        .collect { isNearEnd ->
            if (isNearEnd && !isPaginating) {
                onLoadMore()
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = baseSizes.size20.dimension),
        verticalArrangement = Arrangement.spacedBy(baseSizes.size10.dimension)
    ) {
        item {
            DesignSearchBar(
                modifier = Modifier.padding(top = baseSizes.size10.dimension),
                query = searchQuery,
                onQueryChange = onQueryChange,
                placeholderText = stringResource(R.string.search_placeholder)
            )
        }
        
        item {
            DesignText(
                text = stringResource(R.string.breaking_news_title),
                typography = baseTypographies.textBaseBlackBig,
                textColor = baseColors.primaryColor
            )
        }
        item {
            DesignHighlightImageCard(
                modifier = Modifier.clickable { 
                    onNavigateToDetail(firstArticle.id) 
                },
                image = firstArticle.imageUrl ?: String.empty(),
                title = firstArticle.title,
                leftDescription = firstArticle.authors.firstOrNull()?.name ?: String.empty(),
                rightDescription = firstArticle.publishedAt
            )
        }

        item {
            DesignText(
                text = stringResource(R.string.see_more_title),
                typography = baseTypographies.textBaseBlackSmall,
                textColor = baseColors.primaryColor
            )
        }

        val remainingArticles = articles.drop(1)
        itemsIndexed(
            items = remainingArticles,
            key = { index, article -> article.id }
        ) { _, article ->
            ArticleItem(
                imageUrl = article.imageUrl,
                title = article.title,
                authors = article.authors,
                publishedAt = article.publishedAt,
                onClick = {
                    onNavigateToDetail(article.id)
                }
            )
        }
        
        if (isPaginating) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(baseSizes.size10.dimension),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag("paginationLoading"),
                        color = baseColors.actionColor.color
                    )
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(baseSizes.size20.dimension))
        }
    }
}