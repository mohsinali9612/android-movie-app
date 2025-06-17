package com.example.core_data.domain.models.response_models.media

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SearchMultiResponse(

    @SerialName("page") var page: Int? = null,
    @SerialName("results") var results: List<MediaDataModel> = listOf(),
    @SerialName("total_pages") var totalPages: Int? = null,
    @SerialName("total_results") var totalResults: Int? = null

)