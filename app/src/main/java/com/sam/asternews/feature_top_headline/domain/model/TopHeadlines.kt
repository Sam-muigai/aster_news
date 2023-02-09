package com.sam.asternews.feature_top_headline.domain.model

data class TopHeadlines(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)