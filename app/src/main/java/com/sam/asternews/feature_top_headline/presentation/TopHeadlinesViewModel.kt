package com.sam.asternews.feature_top_headline.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.asternews.core.SavedNewsViewModel
import com.sam.asternews.core.domain.SavedNewsRepository
import com.sam.asternews.core.domain.UserThemePreferenceRepository
import com.sam.asternews.feature_top_headline.data.model.toTopHeadlines
import com.sam.asternews.feature_top_headline.domain.TopHeadlineRepository
import com.sam.asternews.utils.OneTimeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val repository: TopHeadlineRepository,
    userThemePreferenceRepository: UserThemePreferenceRepository,
    localRepository: SavedNewsRepository
):SavedNewsViewModel(userThemePreferenceRepository,localRepository) {

    var state:MutableState<TopHeadlinesUiState> = mutableStateOf(TopHeadlinesUiState.Loading)
        private set

    private var _uiState = Channel<OneTimeEvents>()
    val uiState = _uiState.receiveAsFlow()

    init {
        getTopHeadlines()
    }



    fun pop(){
        sendUiEvent(OneTimeEvents.PopBackStack)
    }



    private fun getTopHeadlines() {
        viewModelScope.launch {
            state.value = TopHeadlinesUiState.Loading
            try {
                repository.getTopHeadlines().collect{
                    state.value = TopHeadlinesUiState.Success(
                        it.toTopHeadlines()
                    )
                }
            }catch (exc :Exception){
                state.value = TopHeadlinesUiState.Error
            }
        }
    }

    private fun sendUiEvent(events: OneTimeEvents){
        viewModelScope.launch {
            _uiState.send(events)
        }
    }

}