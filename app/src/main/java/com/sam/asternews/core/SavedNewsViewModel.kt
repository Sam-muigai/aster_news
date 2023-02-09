package com.sam.asternews.core

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.asternews.core.data.local.model.toSaveNews
import com.sam.asternews.core.domain.SavedNewsRepository
import com.sam.asternews.core.domain.UserThemePreferenceRepository
import com.sam.asternews.core.domain.model.SavedNews
import com.sam.asternews.core.domain.model.toSaveNewsDb
import com.sam.asternews.ui.theme.AsterNewsThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


open class SavedNewsViewModel @Inject constructor(
    private val userThemePreferenceRepository: UserThemePreferenceRepository,
    private val repository: SavedNewsRepository
):ViewModel() {
    val savedNews = repository.getAllSavedNews().map {
        it.map { savedNewsDb ->
            savedNewsDb.toSaveNews()
        }
    }
    var themeState: StateFlow<ThemeState> =
        userThemePreferenceRepository.isDarkTheme.map { isDarkTheme ->
            ThemeState(isDarkTheme = isDarkTheme)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeState()
        )

    fun setTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            userThemePreferenceRepository.setTheme(isDarkTheme)
        }
    }

    fun saveNews(new: SavedNews){
        viewModelScope.launch {
            repository.insertNews(new.toSaveNewsDb())
        }
    }

    fun deleteNews(new: SavedNews){
        viewModelScope.launch {
            repository.deleteNews(new.toSaveNewsDb())
        }
    }

    val tryAgain = mutableStateOf(false)

}
data class ThemeState(
    var isDarkTheme: Boolean = false,
    var theme: Boolean = if (isDarkTheme)
        AsterNewsThemeSettings.isDarkThemeEnabled else
        !AsterNewsThemeSettings.isDarkThemeEnabled
)