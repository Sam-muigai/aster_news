package com.sam.asternews.core.domain

import com.sam.asternews.core.data.local.model.SavedNewsDb
import kotlinx.coroutines.flow.Flow


interface SavedNewsRepository {


    fun getAllSavedNews():Flow<List<SavedNewsDb>>

    suspend fun insertNews(savedNotesDb: SavedNewsDb)

    suspend fun deleteNews(savedNotesDb: SavedNewsDb)

}