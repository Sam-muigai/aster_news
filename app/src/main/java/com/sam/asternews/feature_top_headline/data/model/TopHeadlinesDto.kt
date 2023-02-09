package com.sam.asternews.feature_top_headline.data.model

import com.sam.asternews.feature_top_headline.domain.model.TopHeadlines

data class TopHeadlinesDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)


fun TopHeadlinesDto.toTopHeadlines(): TopHeadlines {
    return TopHeadlines(
        articles = articles.map { articleDto ->
            articleDto.toArticle()
        },
        status = status,
        totalResults = totalResults
    )
}

