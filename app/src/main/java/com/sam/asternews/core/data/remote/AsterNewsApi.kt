package com.sam.asternews.core.data.remote

import com.sam.asternews.feature_home.data.model.CategoryNewsDto
import com.sam.asternews.feature_top_headline.data.model.TopHeadlinesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AsterNewsApi {
    @GET("/v2/top-headlines?sources=bbc-news&apiKey=fbe720bb652d4a5e93ce64fff1dfe814")
    suspend fun getTopHeadlines(): TopHeadlinesDto

    @GET("v2/everything")
    suspend fun getCategoriesNews(
        @Query("q") category :String,
        @Query("apiKey") apiKey:String = "fbe720bb652d4a5e93ce64fff1dfe814"
    ): CategoryNewsDto

}