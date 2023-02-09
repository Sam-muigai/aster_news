package com.sam.asternews.core.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sam.asternews.core.domain.UserThemePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val THEME_PREFERENCE = "theme_preference"
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name= THEME_PREFERENCE)
class UserThemePreferenceRepositoryImpl @Inject constructor(
    private val context: Context
) : UserThemePreferenceRepository {

    private companion object{
        val IS_DARK_THEME = booleanPreferencesKey("dark_theme")
        const val TAG = "UserPreferences"
    }

    override suspend fun setTheme(isDarkTheme:Boolean){
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
        }
    }

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading from datastore", it)
            }else{
                throw it
            }
        }
        .map { preferences ->
        preferences[IS_DARK_THEME] ?: false
    }
}