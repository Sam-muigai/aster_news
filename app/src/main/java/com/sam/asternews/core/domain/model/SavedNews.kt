package com.sam.asternews.core.domain.model

import com.sam.asternews.core.data.local.model.SavedNewsDb


data class SavedNews(
    val title:String,
    val description:String,
    val content:String
)

fun SavedNews.toSaveNewsDb(): SavedNewsDb {
    return SavedNewsDb(
        title = title,
        description = description,
        content = content
    )
}

val savedNewsExample = SavedNews(
    title = "Japan was the future but it's stuck in the past",
    description = "The so-called lost decade has now stretched to three. What went wrong, asks Rupert Wingfield-Hayes.",
    content = "In Japan, houses are like cars.As soon as you move in, your new home is worth less than what you paid for it and after you've finished paying off your mortgage in 40 years"
)