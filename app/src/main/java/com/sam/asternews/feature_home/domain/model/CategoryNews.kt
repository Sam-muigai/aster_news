package com.sam.asternews.feature_home.domain.model


data class CategoryNews(
    val articles: List<CategoryArticle>,
    val status: String,
    val totalResults: Int
)