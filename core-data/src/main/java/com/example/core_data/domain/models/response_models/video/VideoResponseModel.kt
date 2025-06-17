package com.example.core_data.domain.models.response_models.video

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class VideoDataResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("results") val results: ArrayList<VideoModel> = arrayListOf()
)