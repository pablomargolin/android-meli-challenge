package com.margo.news_feed.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article
import com.margo.domain.model.Author
import com.margo.shared_ui.SpaceFlightTheme
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class NewsFeedScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun newsFeedScreen_whenLoading_showsLoadingIndicator() {
        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsFeedScreen(
                    uiState = NewsFeedUiState.Loading,
                    searchQuery = "",
                    onQueryChange = {},
                    onNavigateToDetail = {},
                    onLoadMore = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Retry").assertDoesNotExist()
        composeTestRule.onNodeWithTag("needScreenLoading").assertIsDisplayed()
    }

    @Test
    fun newsFeedScreen_whenError_showsErrorMessageAndRetryButton() {
        var retryClicked = false

        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsFeedScreen(
                    uiState = NewsFeedUiState.Error(ErrorType.NO_INTERNET),
                    searchQuery = "",
                    onQueryChange = {},
                    onNavigateToDetail = {},
                    onLoadMore = {},
                    onRetry = { retryClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").performClick()
        assertTrue(retryClicked)
    }

    @Test
    fun newsFeedScreen_whenSuccessEmpty_showsEmptyState() {
        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsFeedScreen(
                    uiState = NewsFeedUiState.Success(emptyList()),
                    searchQuery = "Mars",
                    onQueryChange = {},
                    onNavigateToDetail = {},
                    onLoadMore = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Mars").assertIsDisplayed()
        composeTestRule.onNodeWithText("There is no news at this time.").assertIsDisplayed()
    }

    @Test
    fun newsFeedScreen_whenSuccessWithData_showsArticlesAndNavigates() {
        var navigatedId: Int? = null
        val testArticles = listOf(
            Article(
                id = 1,
                title = "title1",
                authors = listOf(Author("name1")),
                url = "url",
                imageUrl = "image",
                newsSite = "site",
                summary = "summary",
                publishedAt = "1/1"
            ),
            Article(
                id = 2,
                title = "title2",
                authors = listOf(Author("name2")),
                url = "url",
                imageUrl = "image",
                newsSite = "site",
                summary = "summary",
                publishedAt = "2/2"
            )
        )

        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsFeedScreen(
                    uiState = NewsFeedUiState.Success(testArticles),
                    searchQuery = "",
                    onQueryChange = {},
                    onNavigateToDetail = { navigatedId = it },
                    onLoadMore = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Breaking News").assertIsDisplayed()
        composeTestRule.onNodeWithText("title1").assertIsDisplayed()
        composeTestRule.onNodeWithText("title2").assertIsDisplayed()
        composeTestRule.onNodeWithText("title2").performClick()
        assertTrue(navigatedId == 2)
    }

    @Test
    fun newsFeedScreen_whenPaginating_showsPaginationLoader() {
        val testArticles = listOf(
            Article(
                id = 1,
                title = "title1",
                authors = listOf(Author("name1")),
                url = "url",
                imageUrl = "image",
                newsSite = "site",
                summary = "summary",
                publishedAt = "1/1"
            )
        )

        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsFeedScreen(
                    uiState = NewsFeedUiState.Success(testArticles, isPaginating = true),
                    searchQuery = "",
                    onQueryChange = {},
                    onNavigateToDetail = {},
                    onLoadMore = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("paginationLoading").assertIsDisplayed()
    }
}
