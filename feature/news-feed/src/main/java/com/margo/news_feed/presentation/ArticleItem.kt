package com.margo.news_feed.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.margo.domain.model.Author
import com.margo.domain.utils.empty
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies
import com.margo.shared_ui.components.DesignCard
import com.margo.shared_ui.components.DesignText

import androidx.compose.foundation.clickable

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String?,
    authors: List<Author>?,
    publishedAt: String?,
    onClick: () -> Unit
) {
    DesignCard(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick() },
        backgroundColor = baseColors.transparentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(baseSizes.size05.dimension)
                    .weight(1f)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .crossfade(500)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(baseSizes.size10.dimension)
            ) {
                DesignText(
                    text = title ?: String.empty(),
                    typography = baseTypographies.textBaseBoldSmall,
                    textColor = baseColors.primaryColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DesignText(
                        modifier = Modifier
                            .weight(1f),
                        text = authors?.first()?.name ?: String.empty(),
                        typography = baseTypographies.textBaseNormalXSmall,
                        textColor = baseColors.primaryColor,
                        maxLines = 1
                    )
                    DesignText(
                        modifier = Modifier
                            .weight(1f),
                        text = publishedAt ?: String.empty(),
                        typography = baseTypographies.textBaseNormalXSmall,
                        textColor = baseColors.primaryColor,
                        textAlign = TextAlign.End,
                        maxLines = 1
                    )
                }

            }
        }

    }
}