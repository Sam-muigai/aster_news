package com.sam.asternews.feature_top_headline.presentation

import com.sam.asternews.feature_top_headline.domain.model.TopHeadlines

sealed class TopHeadlinesUiState(){
    object Loading:TopHeadlinesUiState()
    object Error:TopHeadlinesUiState()
    data class Success(val data:TopHeadlines):TopHeadlinesUiState()
}
