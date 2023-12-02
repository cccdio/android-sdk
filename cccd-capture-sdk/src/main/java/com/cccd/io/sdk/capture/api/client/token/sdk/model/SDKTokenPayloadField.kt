package com.cccd.io.sdk.capture.api.client.token.sdk.model

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
@Suppress("SerialVersionUIDInSerializableClass")
data class SDKTokenPayloadField(
    @SerialName("app")
    val applicantId: String,

    @SerialName("client_uuid")
    val clientUuid: String,
) : Serializable