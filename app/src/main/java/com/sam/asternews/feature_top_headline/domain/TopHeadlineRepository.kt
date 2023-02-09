package com.sam.asternews.feature_top_headline.domain

import com.sam.asternews.feature_top_headline.data.model.TopHeadlinesDto
import kotlinx.coroutines.flow.Flow

interface TopHeadlineRepository{

     fun getTopHeadlines():Flow<TopHeadlinesDto>

}