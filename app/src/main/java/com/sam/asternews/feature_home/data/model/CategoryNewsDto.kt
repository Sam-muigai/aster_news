package com.sam.asternews.feature_home.data.model

import com.sam.asternews.feature_home.domain.model.CategoryNews

data class CategoryNewsDto(
    val articles: List<CategoryArticleDto>,
    val status: String,
    val totalResults: Int
)


fun CategoryNewsDto.toCategoryNews(): CategoryNews {
    return CategoryNews(
        articles = articles.map {
            it.toCategoryArticle()
        },
        status = status,
        totalResults = totalResults
    )
}