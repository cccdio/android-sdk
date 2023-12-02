package com.cccd.io.sdk.capture.api.client.token.sdk.model

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
@Suppress("SerialVersionUIDInSerializableClass")
data class SDKTokenUrl(
    @SerialName("cccd_api_url")
    val baseUrl: String? = null,

    @SerialName("auth_url")
    val authUrl: String? = null,
) : Serializable