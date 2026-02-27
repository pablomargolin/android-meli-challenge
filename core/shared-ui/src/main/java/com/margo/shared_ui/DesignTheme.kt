package com.margo.shared_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.margo.shared_ui.theme.ColorTheme
import com.margo.shared_ui.theme.LocalColorTheme
import com.margo.shared_ui.theme.LocalSizeTheme
import com.margo.shared_ui.theme.LocalTypographyTheme
import com.margo.shared_ui.theme.SizeTheme
import com.margo.shared_ui.theme.TypographyTheme
import com.margo.shared_ui.themes.SpaceFlightColorTheme
import com.margo.shared_ui.themes.SpaceFlightSizeTheme
import com.margo.shared_ui.themes.SpaceFlightTypographyTheme

object ThemeScope {
    val baseColors: ColorTheme @Composable get() = LocalColorTheme.current
    val baseSizes: SizeTheme @Composable get() = LocalSizeTheme.current
    val baseTypographies: TypographyTheme @Composable get() = LocalTypographyTheme.current
}

@Composable
fun SpaceFlightTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorTheme provides SpaceFlightColorTheme,
        LocalSizeTheme provides SpaceFlightSizeTheme,
        LocalTypographyTheme provides SpaceFlightTypographyTheme
    ) {
        content()
    }
}