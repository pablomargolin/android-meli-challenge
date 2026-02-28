package com.margo.shared_ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.margo.shared_ui.SpaceFlightTheme
import org.junit.Rule
import org.junit.Test
import com.margo.shared_ui.ThemeScope

class DesignTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun designText_displaysGivenText() {
        val text = "text"
        
        composeTestRule.setContent {
            SpaceFlightTheme {
                val textColor = ThemeScope.baseColors.blackColor
                val typography = ThemeScope.baseTypographies.textBaseNormal
                
                DesignText(
                    text = text,
                    typography = typography,
                    textColor = textColor
                )
            }
        }

        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
}
