package com.sam.asternews.core.domain

import kotlinx.coroutines.flow.Flow

interface UserThemePreferenceRepository {

    suspend fun setTheme(isDarkTheme:Boolean)

    val isDarkTheme:Flow<Boolean>
}