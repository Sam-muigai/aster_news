package com.sam.asternews.core.data.remote

import com.sam.asternews.feature_home.data.model.CategoryNewsDto
import com.sam.asternews.feature_top_headline.data.model.TopHeadlinesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AsterNewsApi {
    @GET("/v2/top-headlines?sources=bbc-news")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = "YOUR_API_KEY"
    ): TopHeadlinesDto

    @GET("v2/everything")
    suspend fun getCategoriesNews(
        @Query("q") category :String,
        @Query("apiKey") apiKey:String = "YOUR_API_KEY"
    ): CategoryNewsDto

}