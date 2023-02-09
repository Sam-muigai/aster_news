package com.sam.asternews.feature_top_headline.data

import android.util.Log
import com.sam.asternews.core.data.remote.AsterNewsApi
import com.sam.asternews.feature_top_headline.data.model.TopHeadlinesDto
import com.sam.asternews.feature_top_headline.domain.TopHeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopHeadlinesRepositoryImpl @Inject constructor(
    private val api: AsterNewsApi
):TopHeadlineRepository {

    override fun getTopHeadlines(): Flow<TopHeadlinesDto> = flow {
        val apiResult = api.getTopHeadlines()
        Log.d("DATA",apiResult.toString())
        emit(apiResult)
    }

}