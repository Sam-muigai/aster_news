package com.sam.asternews.core.data.local

import com.sam.asternews.core.data.local.SavedNewsDao
import com.sam.asternews.core.data.local.model.SavedNewsDb
import com.sam.asternews.core.domain.SavedNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsRepositoryImpl @Inject constructor(
    private val dao: SavedNewsDao
) : SavedNewsRepository {
    override fun getAllSavedNews(): Flow<List<SavedNewsDb>> {
        return dao.getAllSavedNews()
    }

    override suspend fun insertNews(savedNotesDb: SavedNewsDb) {
        dao.insertNews(savedNotesDb)
    }

    override suspend fun deleteNews(savedNotesDb: SavedNewsDb) {
        dao.deleteNews(savedNotesDb)
    }
}