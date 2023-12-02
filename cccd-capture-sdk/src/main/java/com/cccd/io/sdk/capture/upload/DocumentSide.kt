package com.cccd.io.sdk.capture.upload

import com.cccd.io.sdk.capture.DocumentType
import com.cccd.io.sdk.capture.api.client.data.DocSide
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentSide(
    @SerialName("id")
    val id: String,
    @SerialName("nfc_supported")
    val nfcSupported: String,
    @SerialName("side")
    val side: DocSide,
    @SerialName("type")
    val type: DocumentType,
)
