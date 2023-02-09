package com.sam.asternews.core.data.local.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam.asternews.core.data.local.SavedNewsDao


@Database(entities = [SavedNewsDb::class], version = 2)
abstract class SavedNewsDatabase:RoomDatabase(){
    abstract val dao: SavedNewsDao
}