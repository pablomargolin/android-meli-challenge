package com.margo.news_detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article
import com.margo.domain.utils.empty
import com.margo.news_detail.R
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies
import com.margo.network.R as NetworkR
import com.margo.shared_ui.components.DesignNavigationBar
import com.margo.shared_ui.components.DesignText

@Composable
fun NewsDetailRoute(
    onBackClick: () -> Unit,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsDetailScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        onRetry = { viewModel.getArticleDetail() }
    )
}

@Composable
internal fun NewsDetailScreen(
    onBackClick: () -> Unit,
    uiState: NewsDetailUiState,
    onRetry: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        containerColor = baseColors.surfaceLightColor.color,
        topBar = {
            DesignNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                text = String.empty(),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onIconAction = onBackClick
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(baseSizes.size20.dimension)
        ) {
            when(uiState) {
                is NewsDetailUiState.Loading -> LoadingScreen()
                is NewsDetailUiState.Success -> ArticleContent(article = uiState.article)
                is NewsDetailUiState.Error -> {
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
            .fillMaxSize(),
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
private fun ArticleContent(article: Article) {
    ArticleDetailItem(article)
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("detailScreenLoading"),
            color = baseColors.actionColor.color
        )
    }
}