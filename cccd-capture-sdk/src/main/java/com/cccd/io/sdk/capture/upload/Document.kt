package com.cccd.io.sdk.capture.upload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
    @SerialName("front")
    val front: DocumentSide,
    @SerialName("back")
    val back: DocumentSide,
)