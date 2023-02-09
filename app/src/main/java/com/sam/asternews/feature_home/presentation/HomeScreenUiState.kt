package com.sam.asternews.feature_home.presentation

import com.sam.asternews.feature_home.domain.model.CategoryNews


sealed class HomeScreenUiState(){
    object Loading:HomeScreenUiState()
    object Error:HomeScreenUiState()
    data class Success(val data: CategoryNews):HomeScreenUiState()
}