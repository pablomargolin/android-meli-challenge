package com.margo.shared_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies

@Composable
fun DesignNavigationBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    iconContentDescription: String? = null,
    onIconAction: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = baseSizes.size20.dimension,
                end = baseSizes.size20.dimension,
                top = baseSizes.size10.dimension
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .shadow(
                            elevation = baseSizes.size10.dimension,
                            shape = RoundedCornerShape(baseSizes.size20.dimension)
                        )
                        .background(baseColors.whiteColor.color)
                        .padding(baseSizes.size05.dimension)
                        .clickable {
                            onIconAction?.invoke()
                        },
                    imageVector = it,
                    contentDescription = iconContentDescription,
                )
            }
            DesignText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(baseSizes.size05.dimension),
                text = text,
                typography = baseTypographies.textBaseXNormal,
                textColor = baseColors.blackColor,
                textAlign = TextAlign.Center
            )
        }

    }
}