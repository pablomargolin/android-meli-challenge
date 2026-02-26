package com.margo.news_feed.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsFeedScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: NewsFeedViewModel = hiltViewModel()
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "NewsFeedScreen"
    )
    Button(onClick = {
        onNavigateToDetail(36510)
    }) {
        Text(text = "Detail")
    }
    viewModel.fetchNews()
}