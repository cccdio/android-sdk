package com.cccd.io.sdk.capture.api.client.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DocSide(
    val id: String
) {
    @SerialName("front")
    FRONT("front"),

    @SerialName("back")
    BACK("back"),
}