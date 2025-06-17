package com.example.core_data.domain.models.dto

import com.example.core_data.domain.models.response_models.media.SearchMultiResponse
import kotlinx.serialization.Serializable

@Serializable
data class SearchMultiDTO(
    val metaData: MetaData?,
    val result : List<MovieTypeDTO>
)

fun SearchMultiResponse.toSearchMultiDTO(): SearchMultiDTO {
    return SearchMultiDTO(
        metaData = toMeta(),
        result = toMovieTypeDTO()
    )
}

