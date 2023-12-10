package com.cccd.io.sdk.capture.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskStartPayload(
    @SerialName("file_name")
    val fileName: String,
    @SerialName("media_type")
    val mediaType: String
)