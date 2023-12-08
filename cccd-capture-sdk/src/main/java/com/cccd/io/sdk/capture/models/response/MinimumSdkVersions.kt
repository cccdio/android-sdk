package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.Serializable

@Serializable
data class MinimumSdkVersions(
    val android: String,
    val ios: String,
    val web: String
)
