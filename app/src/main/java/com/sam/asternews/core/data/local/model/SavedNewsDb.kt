package com.sam.asternews.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.asternews.core.domain.model.SavedNews

@Entity(tableName = "saved_notes")
data class SavedNewsDb(
    @PrimaryKey
    val title:String,
    val description:String,
    val content:String
)

fun SavedNewsDb.toSaveNews(): SavedNews {
    return SavedNews(
        title = title,
        description = description,
        content = content
    )
}
