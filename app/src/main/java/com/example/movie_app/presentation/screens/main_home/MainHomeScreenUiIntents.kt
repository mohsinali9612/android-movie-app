package com.example.movie_app.presentation.screens.main_home

import com.example.core_data.domain.models.response_models.media.MediaDataModel


sealed interface MainHomeScreenUiIntents {
    sealed interface ButtonIntents{
        data object OnSearchButtonIntent : MainHomeScreenUiIntents
    }
    sealed interface TextFieldsIntent{
        @JvmInline
        value class OnSearchTextUpdate(val search:String) : MainHomeScreenUiIntents
    }
    sealed interface ListItemIntent{
        @JvmInline
        value class OnMovieItemClick(val media: MediaDataModel) : MainHomeScreenUiIntents

        data object OnTriggerPagination : MainHomeScreenUiIntents
    }


}