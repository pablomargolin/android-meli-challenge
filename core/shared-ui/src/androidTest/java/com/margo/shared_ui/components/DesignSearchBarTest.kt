package com.margo.shared_ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsDisplayed
import com.margo.shared_ui.SpaceFlightTheme
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class DesignSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun designSearchBar_displaysPlaceholder() {
        val placeholder = "Search here..."

        composeTestRule.setContent {
            SpaceFlightTheme {
                DesignSearchBar(
                    query = "",
                    onQueryChange = {},
                    placeholderText = placeholder
                )
            }
        }

        composeTestRule.onNodeWithText(placeholder).assertIsDisplayed()
    }

    @Test
    fun designSearchBar_updatesQueryOnInput() {
        var queryState = ""

        composeTestRule.setContent {
            SpaceFlightTheme {
                DesignSearchBar(
                    query = queryState,
                    onQueryChange = { queryState = it },
                    placeholderText = "Search..."
                )
            }
        }

        val testInput = "Mars"

        composeTestRule.onNodeWithText("Search...").performTextInput(testInput)
        assertEquals(testInput, queryState)
    }
}
