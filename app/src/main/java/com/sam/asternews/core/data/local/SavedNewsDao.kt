package com.sam.asternews.core.data.local

import androidx.room.*
import com.sam.asternews.core.data.local.model.SavedNewsDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {

    @Query("SELECT * FROM saved_notes")
    fun getAllSavedNews(): Flow<List<SavedNewsDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(savedNotesDb: SavedNewsDb)

    @Delete
    suspend fun deleteNews(savedNotesDb: SavedNewsDb)
}