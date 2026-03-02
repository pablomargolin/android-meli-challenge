package com.margo.news_detail.data.mapper

import com.margo.domain.model.Author
import com.margo.news_detail.data.remote.dto.AuthorDetailDto

/**
 * Maps an [AuthorDetailDto] from the data layer into an [Author] for the domain layer.
 */
fun AuthorDetailDto.toDomain(): Author {
    return Author(
        name = name
    )
}