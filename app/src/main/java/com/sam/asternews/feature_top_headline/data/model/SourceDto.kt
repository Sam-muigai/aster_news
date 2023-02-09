package com.sam.asternews.feature_top_headline.data.model

import com.sam.asternews.feature_top_headline.domain.model.Source

data class SourceDto(
    val id: String,
    val name: String
)

fun SourceDto.toSource() : Source {
    return Source(
        id = id,
        name = name
    )
}