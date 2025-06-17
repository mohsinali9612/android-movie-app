package com.example.core_data.datasource.utils

import com.example.core_data.datasource.enums.MediaTypeEnums
import com.example.core_data.datasource.enums.VideoSourceEnums


fun String?.isVideoContent(): Boolean {
    return this?.lowercase() in listOf(MediaTypeEnums.TV.mediaType, MediaTypeEnums.MOVIE.mediaType)
}

fun getPlayableVideoUrl(site: String, key: String): String? {
    return when (site) {
        VideoSourceEnums.YOUTUBE.sourceType -> "${VideoSourceEnums.YOUTUBE.sourceUrl}$key"
        VideoSourceEnums.VIMEO.sourceType -> "${VideoSourceEnums.VIMEO.sourceUrl}$key"
        else -> null
    }
}
