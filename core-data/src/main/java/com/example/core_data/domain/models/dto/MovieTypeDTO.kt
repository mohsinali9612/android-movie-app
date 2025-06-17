package com.example.core_data.domain.models.dto

import com.example.core_data.domain.models.response_models.media.MediaDataModel
import com.example.core_data.domain.models.response_models.media.SearchMultiResponse
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class MovieTypeDTO(
    val mediaType: String,
    val mediaItems: List<MediaDataModel>
)


fun SearchMultiResponse.toMovieTypeDTO(): List<MovieTypeDTO> {

    return results
        .filter {
            it.id != null && !it.mediaType.isNullOrBlank()
        }
        .distinctBy {
            Pair(
                (it.id),
                it.mediaType?.trim()
            )
        }
        .groupBy { it.mediaType }
        .map { (mediaType, items) ->
            MovieTypeDTO(
                mediaType = mediaType ?: "unknown",
                mediaItems = items.sortedBy { item ->
                    item.title ?: item.originalTitle ?: item.name ?: item.originalName ?: ""
                }
            )
        }
}

