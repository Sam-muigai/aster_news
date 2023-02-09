package com.sam.asternews.feature_top_headline.data.model

import com.sam.asternews.feature_top_headline.domain.model.Article

data class ArticleDto(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDto,
    val title: String,
    val url: String,
    val urlToImage: String
)

fun ArticleDto.toArticle() : Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.toSource(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}
