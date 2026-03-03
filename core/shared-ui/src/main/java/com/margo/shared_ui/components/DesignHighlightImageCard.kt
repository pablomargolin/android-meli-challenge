package com.margo.shared_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies

@Composable
fun DesignHighlightImageCard(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    leftDescription: String,
    rightDescription: String,
    scale: HighlightImageCardScale = HighlightImageCardScale.FILL,
    contentScale: HighlightImageCardContentScale = HighlightImageCardContentScale.CROP
) {
    DesignCard(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(baseSizes.size05.dimension)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .crossfade(500)
                    .scale(scale.value)
                    .build(),
                contentDescription = title,
                contentScale = contentScale.value
            )
            DesignText(
                modifier = Modifier.padding(baseSizes.size10.dimension),
                text = title,
                typography = baseTypographies.textBaseBlackSmall,
                textColor = baseColors.primaryColor
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DesignText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(baseSizes.size10.dimension)
                        .weight(1f),
                    text = leftDescription,
                    typography = baseTypographies.textBaseNormalSmall,
                    textColor = baseColors.primaryColor
                )
                DesignText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(baseSizes.size10.dimension)
                        .weight(1f),
                    text = rightDescription,
                    typography = baseTypographies.textBaseNormalSmall,
                    textColor = baseColors.primaryColor,
                    textAlign = TextAlign.End
                )
            }

        }
    }
}

enum class HighlightImageCardScale(val value: Scale) {
    FILL(Scale.FILL),
    FIT(Scale.FIT)
}

enum class HighlightImageCardContentScale(val value: ContentScale) {
    FIT(ContentScale.Fit),
    FILL(ContentScale.FillBounds),
    CROP(ContentScale.Crop),
    INSIDE(ContentScale.Inside)
}