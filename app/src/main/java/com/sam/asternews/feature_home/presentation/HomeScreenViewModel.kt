package com.sam.asternews.feature_home.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sam.asternews.core.SavedNewsViewModel
import com.sam.asternews.core.domain.SavedNewsRepository
import com.sam.asternews.feature_home.data.model.toCategoryNews
import com.sam.asternews.feature_home.domain.CategoriesNewsRepository
import com.sam.asternews.core.domain.UserThemePreferenceRepository
import com.sam.asternews.ui.theme.AsterNewsThemeSettings
import com.sam.asternews.utils.OneTimeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: CategoriesNewsRepository,
    localRepository: SavedNewsRepository,
    userThemePreferenceRepository: UserThemePreferenceRepository
) : SavedNewsViewModel(userThemePreferenceRepository,localRepository) {

    var state: MutableState<HomeScreenUiState> = mutableStateOf(HomeScreenUiState.Loading)
        private set

    private var _uiState = Channel<OneTimeEvents>()
    val uiState = _uiState.receiveAsFlow()

    init {
        getCategoryNews("android")
    }

    fun showSnackBar(message: String, action: String = "") {
        sendUiEvents(OneTimeEvents.SnackBar(message, action))
    }

    fun navigate(route: String) {
        sendUiEvents(OneTimeEvents.Navigation(route))
    }


    fun pop() {
        sendUiEvents(OneTimeEvents.PopBackStack)
    }

    fun getCategoryNews(category: String) {
        viewModelScope.launch {
            state.value = HomeScreenUiState.Loading
            try {
                repository.getCategoriesNews(category).collect {
                    Log.d("DATA", it.toCategoryNews().toString())
                    state.value = HomeScreenUiState.Success(it.toCategoryNews())
                }
            } catch (exc: Exception) {
                exc.localizedMessage?.let { Log.d("ERRORVM", it) }
                state.value = HomeScreenUiState.Error
            }
        }
    }


    private fun sendUiEvents(events: OneTimeEvents) {
        viewModelScope.launch {
            _uiState.send(events)
        }
    }

}








