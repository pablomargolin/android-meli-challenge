package com.margo.news_detail.data.mapper

import com.margo.domain.model.Author
import com.margo.news_detail.data.remote.dto.AuthorDetailDto

fun AuthorDetailDto.toDomain(): Author {
    return Author(
        name = name
    )
}