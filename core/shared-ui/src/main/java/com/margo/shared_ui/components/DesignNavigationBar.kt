package com.margo.shared_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.margo.shared_ui.ThemeScope.baseColors
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
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
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
                .align(Alignment.Center),
            text = text,
            typography = baseTypographies.textBaseNormal,
            textColor = baseColors.blackColor,
            textAlign = TextAlign.Center
        )
    }
}