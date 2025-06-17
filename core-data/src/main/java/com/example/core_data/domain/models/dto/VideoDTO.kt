package com.example.core_data.domain.models.dto

import com.example.core_data.domain.models.response_models.video.VideoModel
import com.example.core_data.datasource.utils.getPlayableVideoUrl
import com.example.utils_extension.utils.ExtConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDTO(
    @SerialName("name") val name: String? = null,
    @SerialName("site") val site: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("key") val key: String? = null,
    @SerialName("id") val id: String? = null,
    val videoUrl: String?=null
)

fun VideoModel.toVideoDTO(): VideoDTO {
    return VideoDTO(
        name = name,
        site = site,
        type = type,
        key=key,
        id = id,
        videoUrl = getPlayableVideoUrl(site?: ExtConstants.StringConstants.EMPTY,key?:ExtConstants.StringConstants.EMPTY)
    )
}