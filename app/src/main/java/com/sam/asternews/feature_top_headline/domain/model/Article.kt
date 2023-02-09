package com.sam.asternews.feature_top_headline.domain.model

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

val articleExample= Article(
    author = "BBC News",
    content = "In Japan, houses are like cars.As soon as you move in, your new home is worth less than what you paid for it and after you've finished paying off your mortgage in 40 years,",
    description = "The so-called lost decade has now stretched to three. What went wrong, asks Rupert Wingfield-Hayes.",
    publishedAt = "2023-01-21T15:37:27.4688758Z" ,
    source = Source(id = "bbc-news", name = "BBC News"),
    title = "Japan was the future but it's stuck in the past",
    url = "https://www.bbc.com/news/world-asia-63830490",
    urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/A189/production/_128235314_gettyimages-1246104180.jpg" ,
)




