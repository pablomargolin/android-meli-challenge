package com.margo.shared_ui.themes

import androidx.compose.ui.graphics.Color
import com.margo.shared_ui.theme.ColorTheme

val SpaceFlightColorTheme: ColorTheme = object: ColorTheme() {
    override val primaryColor: BaseColor = BaseColor(color = Color(0xFF3D4165))
    override val blackColor: BaseColor = BaseColor(color = Color(0xFF000000))
    override val surfaceColor: BaseColor = BaseColor(color = Color(0xFFFCFBFF))
    override val surfaceLightColor: BaseColor = BaseColor(color = Color(0xFFF8F8F8))
    override val whiteColor: BaseColor = BaseColor(color = Color(0xFFFFFFFF))
    override val actionColor: BaseColor = BaseColor(color = Color(0xFF1C998E))
    override val transparentColor: BaseColor = BaseColor(color = Color(0x00000000))
}