package com.margo.news_detail.data.mapper

import com.margo.news_detail.data.remote.dto.ArticleDetailDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleDetailMapperTest {
    @Test
    fun `toDomain maps Dto to Article correctly`() {
        val dto = ArticleDetailDto(
            id = 1,
            title = "title",
            summary = "summary",
            imageUrl = "url",
            publishedAt = "published"
        )
        val domain = dto.toDomain()

        assertEquals(1, domain.id)
        assertEquals("title", domain.title)
        assertEquals("summary", domain.summary)
        assertEquals("url", domain.imageUrl)
        assertEquals("published", domain.publishedAt)
        assertNull(domain.authors)
        assertNull(domain.newsSite)
        assertNull(domain.url)
    }

    @Test
    fun `toDomain handles null fields correctly`() {
        val dto = ArticleDetailDto(
            id = 2,
            title = "title 2",
            summary = "summary 2",
            imageUrl = null,
            publishedAt = null
        )
        val domain = dto.toDomain()

        assertEquals("", domain.imageUrl)
        assertEquals("", domain.publishedAt)
    }
}
