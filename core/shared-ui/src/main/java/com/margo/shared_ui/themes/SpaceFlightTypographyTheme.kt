package com.margo.shared_ui.themes

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.margo.shared_ui.R
import com.margo.shared_ui.theme.TypographyTheme
import com.margo.shared_ui.foundation.Typography

val SpaceFlightTypographyTheme: TypographyTheme = object: TypographyTheme() {
    override val textBaseNormal: Typography = Typography(FontFamily(Font(resId = R.font.segma_medium)), 24.sp)
    override val textBaseNormalSmall: Typography = Typography(FontFamily(Font(resId = R.font.segma_medium)), 12.sp)
    override val textBaseBold: Typography = Typography(FontFamily(Font(resId = R.font.segma_bold)), 24.sp)
    override val textBaseBoldSmall: Typography = Typography(FontFamily(Font(resId = R.font.segma_bold)), 12.sp)
    override val textBaseBlack: Typography = Typography(FontFamily(Font(resId = R.font.segma_black)), 24.sp)
    override val textBoldItalic: Typography = Typography(FontFamily(Font(resId = R.font.segma_bold_italic)), 24.sp)
    override val textBoldItalicSmall: Typography = Typography(FontFamily(Font(resId = R.font.segma_bold_italic)), 12.sp)
    override val textBlackItalic: Typography = Typography(FontFamily(Font(resId = R.font.segma_black_italic)), 24.sp)

}