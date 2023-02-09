package com.sam.asternews.feature_home.data.model

import com.sam.asternews.feature_home.domain.model.CategoryArticle

data class CategoryArticleDto(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: CategorySourceDto,
    val title: String,
    val url: String,
    val urlToImage: String?
)

fun CategoryArticleDto.toCategoryArticle():CategoryArticle{
    return CategoryArticle(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.toCategorySource(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}