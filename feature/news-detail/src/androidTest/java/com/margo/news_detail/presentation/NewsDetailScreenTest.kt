package com.margo.news_detail.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.margo.domain.common.ErrorType
import com.margo.domain.model.Article
import com.margo.domain.model.Author
import com.margo.shared_ui.SpaceFlightTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NewsDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun newsDetailScreen_whenLoading_showsLoadingIndicator() {
        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsDetailScreen(
                    onBackClick = {},
                    uiState = NewsDetailUiState.Loading,
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Retry").assertDoesNotExist()
        composeTestRule.onNodeWithTag("detailScreenLoading").assertIsDisplayed()
    }

    @Test
    fun newsDetailScreen_whenError_showsErrorMessageAndRetryButton() {
        var retryClicked = false

        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsDetailScreen(
                    onBackClick = {},
                    uiState = NewsDetailUiState.Error(ErrorType.NO_INTERNET),
                    onRetry = { retryClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").performClick()
        assertTrue(retryClicked)
    }

    @Test
    fun newsDetailScreen_whenSuccess_showsArticleDetails() {
        val testArticle = Article(
            id = 1,
            title = "title",
            authors = listOf(Author("name")),
            url = "url",
            imageUrl = "imageUrl",
            newsSite = "newsSite",
            summary = "summary",
            publishedAt = "published"
        )

        composeTestRule.setContent {
            SpaceFlightTheme {
                NewsDetailScreen(
                    onBackClick = {},
                    uiState = NewsDetailUiState.Success(testArticle),
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithText("title").assertIsDisplayed()
        composeTestRule.onNodeWithText("name").assertIsDisplayed()
        composeTestRule.onNodeWithText("summary").assertIsDisplayed()
        composeTestRule.onNodeWithText("published").assertIsDisplayed()
        composeTestRule.onNodeWithText("newsSite").assertIsDisplayed()
    }
}
