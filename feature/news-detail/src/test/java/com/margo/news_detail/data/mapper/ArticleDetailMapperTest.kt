package com.margo.news_detail.data.mapper

import com.margo.news_detail.data.remote.dto.ArticleDetailDto
import com.margo.news_detail.data.remote.dto.AuthorDetailDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleDetailMapperTest {
    @Test
    fun `toDomain maps Dto to Article correctly`() {
        val dto = ArticleDetailDto(
            id = 1,
            title = "title",
            authors = listOf(AuthorDetailDto("name")),
            summary = "summary",
            imageUrl = "url",
            url = "articleUrl",
            newsSite = "newsSite",
            publishedAt = "published"
        )
        val domain = dto.toDomain()

        assertEquals(1, domain.id)
        assertEquals("title", domain.title)
        assertEquals("summary", domain.summary)
        assertEquals("url", domain.imageUrl)
        assertEquals("published", domain.publishedAt)
        assertEquals(1, domain.authors.size)
        assertEquals("name", domain.authors.first().name)
        assertEquals("newsSite", domain.newsSite)
        assertEquals("articleUrl", domain.url)
    }

    @Test
    fun `toDomain handles null fields correctly`() {
        val dto = ArticleDetailDto(
            id = 2,
            title = "title 2",
            authors = null,
            summary = "summary 2",
            imageUrl = null,
            url = null,
            newsSite = null,
            publishedAt = null
        )
        val domain = dto.toDomain()

        assertNull(domain.imageUrl)
        assertEquals("", domain.publishedAt)
        assertEquals(emptyList<Any>(), domain.authors)
        assertNull(domain.url)
        assertNull(domain.newsSite)
    }
}
