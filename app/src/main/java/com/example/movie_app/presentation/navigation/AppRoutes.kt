package com.example.movie_app.presentation.navigation

import com.example.core_data.domain.models.response_models.media.MediaDataModel
import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoutes {
    @Serializable
    data object MainHome : AppRoutes()

    @Serializable
    data class Detail(
        val mediaDataModel: MediaDataModel
    ) : AppRoutes()

    @Serializable
    data class VideoPlayer(
        val id:Int,
        val mediaType: String
    ) : AppRoutes()
}