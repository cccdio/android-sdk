package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploaderConfig(
    @SerialName("document_selection")
    val documentSelection: UploaderConfigSelection? = null,
    @SerialName("nfc_enabled")
    val nfcEnabled: Boolean? = null,
    val timeout: Int
)
