package com.sam.asternews.feature_home.domain

import com.sam.asternews.feature_home.data.model.CategoryNewsDto
import kotlinx.coroutines.flow.Flow

interface CategoriesNewsRepository {

    suspend fun getCategoriesNews(category:String):Flow<CategoryNewsDto>

}