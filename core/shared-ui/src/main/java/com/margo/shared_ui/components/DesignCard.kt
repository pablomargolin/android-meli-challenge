package com.margo.shared_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.theme.ColorTheme
import com.margo.shared_ui.theme.SizeTheme

@Composable
fun DesignCard(
    modifier: Modifier = Modifier,
    shapeDimension: SizeTheme.BaseSize = baseSizes.size05,
    backgroundColor: ColorTheme.BaseColor = baseColors.whiteColor,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(shapeDimension.dimension)

    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.color
        ),
        border = BorderStroke(1.dp, baseColors.surfaceColor.color)
    ) {
        content()
    }
}