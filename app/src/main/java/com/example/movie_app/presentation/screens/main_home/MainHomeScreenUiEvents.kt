package com.example.movie_app.presentation.screens.main_home

import com.example.core_data.domain.models.response_models.media.MediaDataModel


sealed interface MainHomeScreenUiEvents {
    sealed interface Navigate {
        data class MoveToDetail(val media: MediaDataModel) : MainHomeScreenUiEvents
    }

    sealed interface UserFeedback{
        data class ShowLoader(val isLoading: Boolean): MainHomeScreenUiEvents
    }

}