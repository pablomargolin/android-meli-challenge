package com.margo.shared_ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.material3.Text
import com.margo.shared_ui.SpaceFlightTheme
import com.margo.shared_ui.ThemeScope
import org.junit.Rule
import org.junit.Test

class DesignCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun designCard_displaysContent() {
        val contentText = "Inside Card"

        composeTestRule.setContent {
            SpaceFlightTheme {
                DesignCard {
                    Text(text = contentText)
                }
            }
        }

        composeTestRule.onNodeWithText(contentText).assertIsDisplayed()
    }
}
