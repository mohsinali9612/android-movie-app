package com.example.core_data.domain.models.dto

import com.example.core_data.domain.models.response_models.media.SearchMultiResponse
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    val page: Int? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
)

fun SearchMultiResponse.toMeta(): MetaData {
    return MetaData(
        page = page,
        totalPages=totalPages,
        totalResults=totalResults
    )
}
