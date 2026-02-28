package com.margo.shared_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.margo.shared_ui.foundation.Typography
import com.margo.shared_ui.themes.SpaceFlightTypographyTheme

@Immutable
abstract class TypographyTheme {
    abstract val textBaseNormal: Typography
    abstract val textBaseNormalSmall: Typography
    abstract val textBaseNormalXSmall: Typography
    abstract val textBaseBold: Typography
    abstract val textBaseBoldSmall: Typography
    abstract val textBaseBlackBig: Typography
    abstract val textBaseBlack: Typography
    abstract val textBaseBlackSmall: Typography
    abstract val textBoldItalic: Typography
    abstract val textBoldItalicSmall: Typography
    abstract val textBlackItalic: Typography
}

val LocalTypographyTheme: ProvidableCompositionLocal<TypographyTheme> = staticCompositionLocalOf { SpaceFlightTypographyTheme }