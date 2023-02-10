package com.sam.asternews.feature_home.data

import com.sam.asternews.core.data.remote.AsterNewsApi
import com.sam.asternews.feature_home.data.model.CategoryNewsDto
import com.sam.asternews.feature_home.domain.CategoriesNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoriesNewsRepositoryImpl @Inject constructor(
    private val api: AsterNewsApi
): CategoriesNewsRepository {
    override suspend fun getCategoriesNews(category:String):
            Flow<CategoryNewsDto> = flow {
        val news = api.getCategoriesNews(category = category)
        emit(news)
    }
}