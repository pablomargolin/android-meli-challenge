package com.margo.shared_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import com.margo.shared_ui.themes.SpaceFlightSizeTheme

@Immutable
abstract class SizeTheme {
    abstract val size00: BaseSize
    abstract val size02: BaseSize
    abstract val size04: BaseSize
    abstract val size05: BaseSize
    abstract val size10: BaseSize
    abstract val size15: BaseSize
    abstract val size20: BaseSize
    abstract val size25: BaseSize
    abstract val size200: BaseSize
    @JvmInline
    value class BaseSize(
        val dimension: Dp
    )
}

val LocalSizeTheme: ProvidableCompositionLocal<SizeTheme> = staticCompositionLocalOf { SpaceFlightSizeTheme }