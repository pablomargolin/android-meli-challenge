package com.margo.news_detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.margo.domain.model.Article
import com.margo.domain.utils.empty
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies
import com.margo.shared_ui.components.DesignAnnotatedText
import com.margo.shared_ui.components.DesignText

/**
 * Composable that displays the full details of a specific [Article].
 * Includes the header image, title, author, publication date, summary, and a clickable source URL.
 *
 * @param article The domain model containing the details to display.
 */
@Composable
internal fun ArticleDetailItem(article: Article) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(baseSizes.size200.dimension),
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.imageUrl)
                .crossfade(true)
                .crossfade(500)
                .scale(Scale.FIT)
                .build(),
            contentDescription = article.title,
            contentScale = ContentScale.Crop
        )

        DesignText(
            modifier = Modifier.padding(top = baseSizes.size15.dimension),
            text = article.title,
            typography = baseTypographies.textBaseBlackSmall,
            textColor = baseColors.primaryColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = baseSizes.size20.dimension)
        ) {
            DesignText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = article.authors.firstOrNull()?.name ?: String.empty(),
                typography = baseTypographies.textBaseNormalXSmall,
                textColor = baseColors.primaryColor
            )
            DesignText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = article.publishedAt,
                typography = baseTypographies.textBaseNormalXSmall,
                textColor = baseColors.primaryColor,
                textAlign = TextAlign.End
            )
        }

        DesignText(
            modifier = Modifier.padding(top = baseSizes.size10.dimension),
            text = article.summary,
            typography = baseTypographies.textBaseNormalSmall,
            textColor = baseColors.primaryColor
        )

        ArticleUrlItem(article)
    }
}

@Composable
private fun ArticleUrlItem(article: Article) {
    article.url?.let {
        val annotatedText = buildAnnotatedString {
            withLink(
                link = LinkAnnotation.Url(
                    url = it
                )
            ) {
                withStyle(style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                ) {
                    append(article.newsSite ?: article.url ?: String.empty())
                }
            }
        }

        DesignAnnotatedText(
            modifier = Modifier.padding(top = baseSizes.size10.dimension),
            text = annotatedText,
            typography = baseTypographies.textBaseNormalSmall,
            textColor = baseColors.primaryColor
        )
    }
}