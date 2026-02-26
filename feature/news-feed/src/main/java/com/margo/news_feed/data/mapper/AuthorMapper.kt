package com.margo.news_feed.data.mapper

import com.margo.domain.model.Author
import com.margo.news_feed.data.remote.dto.AuthorDto

fun AuthorDto.toDomain(): Author {
    return Author(
        name = name
    )
}