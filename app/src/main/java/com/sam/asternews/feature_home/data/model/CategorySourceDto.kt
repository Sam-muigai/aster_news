package com.sam.asternews.feature_home.data.model

import com.sam.asternews.feature_home.domain.model.CategorySource

data class CategorySourceDto(
    val id: String? = null,
    val name: String
)

fun CategorySourceDto.toCategorySource(): CategorySource {
    return CategorySource(
        id = id,
        name = name
    )
}