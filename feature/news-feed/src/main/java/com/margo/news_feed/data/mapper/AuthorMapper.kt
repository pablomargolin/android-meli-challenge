package com.margo.news_feed.data.mapper

import com.margo.domain.model.Author
import com.margo.news_feed.data.remote.dto.AuthorDto

/**
 * Maps an [AuthorDto] from the data layer into an [Author] for the domain layer.
 */
fun AuthorDto.toDomain(): Author {
    return Author(
        name = name
    )
}