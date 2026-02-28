package com.margo.shared_ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextOverflow.Companion
import com.margo.shared_ui.theme.ColorTheme
import com.margo.shared_ui.foundation.Typography

@Composable
fun DesignText(
    modifier: Modifier = Modifier,
    text: String,
    typography: Typography,
    textColor: ColorTheme.BaseColor,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor.color,
        fontFamily = typography.fontFamily,
        fontSize = typography.fontSize,
        maxLines = maxLines,
        minLines = minLines,
        textAlign = textAlign,
        style = style,
        overflow = overflow
    )
}