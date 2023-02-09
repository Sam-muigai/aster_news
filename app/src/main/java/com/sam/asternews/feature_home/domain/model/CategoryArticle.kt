package com.sam.asternews.feature_home.domain.model

data class CategoryArticle(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: CategorySource,
    val title: String,
    val url: String,
    val urlToImage: String?
)