package com.margo.news_detail.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsDetailScreen(
    onBackClick: () -> Unit,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "NewsDetailScreen"
    )
    viewModel.getArticleDetail()
}