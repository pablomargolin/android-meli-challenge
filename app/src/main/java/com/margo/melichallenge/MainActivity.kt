package com.margo.melichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.margo.shared_ui.SpaceFlightTheme
import com.margo.shared_ui.ThemeScope
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single, main activity of the application.
 * It serves as the container for Jetpack Compose UI and sets up the root [SpaceFlightNavGraph].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpaceFlightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ThemeScope.baseColors.surfaceColor.color
                ) {
                    SpaceFlightNavGraph()
                }
            }
        }
    }
}