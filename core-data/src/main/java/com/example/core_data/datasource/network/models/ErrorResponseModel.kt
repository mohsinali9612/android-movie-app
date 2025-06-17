package com.example.core_data.datasource.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseModel(
    @SerialName("status_message") val error:String
)
