package com.margo.shared_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.margo.shared_ui.themes.SpaceFlightColorTheme

@Immutable
abstract class ColorTheme {
    abstract val primaryColor: BaseColor
    abstract val blackColor: BaseColor
    abstract val surfaceColor: BaseColor
    abstract val surfaceLightColor: BaseColor
    abstract val whiteColor: BaseColor
    abstract val actionColor: BaseColor
    abstract val transparentColor: BaseColor
    @JvmInline
    value class BaseColor(
        val color: Color
    )
}

val LocalColorTheme: ProvidableCompositionLocal<ColorTheme> = staticCompositionLocalOf { SpaceFlightColorTheme }