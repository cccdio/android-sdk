package com.cccd.io.sdk.capture.ui.options

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FlowAction {
    @SerialName("WELCOME")
    WELCOME,

    @SerialName("USER_CONSENT")
    USER_CONSENT,

    @SerialName("INTRO_FACE_CAPTURE")
    INTRO_FACE_CAPTURE,

    @SerialName("INTRO_LIVENESS_CAPTURE")
    INTRO_LIVENESS_CAPTURE,

    @SerialName("CAPTURE_FACE")
    CAPTURE_FACE,

    @SerialName("CAPTURE_DOCUMENT")
    CAPTURE_DOCUMENT,

    @SerialName("CAPTURE_LIVENESS")
    CAPTURE_LIVENESS,

    @SerialName("CAPTURE_LIVENESS_CONFIRMATION")
    CAPTURE_LIVENESS_CONFIRMATION,

    @SerialName("ACTIVE_VIDEO_CAPTURE")
    ACTIVE_VIDEO_CAPTURE,

    @SerialName("NFC_HOST_FEATURE")
    NFC_HOST_FEATURE,

    @SerialName("FINAL")
    FINAL,

    @SerialName("MESSAGE")
    MESSAGE,

    @SerialName("PROOF_OF_ADDRESS")
    PROOF_OF_ADDRESS,

    @SerialName("DYNAMIC_CONTENT")
    DYNAMIC_CONTENT;
}