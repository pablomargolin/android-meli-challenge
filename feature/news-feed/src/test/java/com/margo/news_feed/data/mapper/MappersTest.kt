package com.margo.news_feed.data.mapper

import com.margo.domain.model.Article
import com.margo.domain.model.Author
import com.margo.news_feed.data.remote.dto.ArticleDto
import com.margo.news_feed.data.remote.dto.AuthorDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `AuthorDto toDomain maps correctly`() {
        val dto = AuthorDto(name = "name")
        val domain = dto.toDomain()

        assertEquals("name", domain.name)
    }

    @Test
    fun `AuthorDto toDomain handles null name`() {
        val dto = AuthorDto(name = null)
        val domain = dto.toDomain()

        assertEquals(null, domain.name)
    }

    @Test
    fun `ArticleDto toDomain maps correctly`() {
        val authorDto = AuthorDto(name = "name")
        val articleDto = ArticleDto(
            id = 1,
            title = "title",
            authors = listOf(authorDto),
            url = "url",
            imageUrl = "imageUrl",
            newsSite = "newsSite",
            summary = "summary",
            publishedAt = "published"
        )

        val expectedDomain = Article(
            id = 1,
            title = "title",
            authors = listOf(Author(name = "name")),
            url = "url",
            imageUrl = "imageUrl",
            newsSite = "newsSite",
            summary = "summary",
            publishedAt = "published"
        )

        val domain = articleDto.toDomain()

        assertEquals(expectedDomain, domain)
    }

    @Test
    fun `ArticleDto toDomain handles null authors list`() {
        val articleDto = ArticleDto(
            id = 1,
            title = "title",
            authors = null,
            url = "url",
            imageUrl = "imageUrl",
            newsSite = "newsSite",
            summary = "summary",
            publishedAt = "published"
        )

        val domain = articleDto.toDomain()

        assertEquals(null, domain.authors)
    }
}