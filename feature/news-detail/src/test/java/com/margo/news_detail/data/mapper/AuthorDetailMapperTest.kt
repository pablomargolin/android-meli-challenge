package com.margo.news_detail.data.mapper

import com.margo.news_detail.data.remote.dto.AuthorDetailDto
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthorDetailMapperTest {
    @Test
    fun `toDomain maps Dto to Author correctly`() {
        val dto = AuthorDetailDto(
            name = "name"
        )
        val domain = dto.toDomain()

        assertEquals("name", domain.name)
    }

    @Test
    fun `toDomain maps Dto to Author correctly when name is null`() {
        val dto = AuthorDetailDto(
            name = null
        )
        val domain = dto.toDomain()

        assertEquals("", domain.name)
    }
}
