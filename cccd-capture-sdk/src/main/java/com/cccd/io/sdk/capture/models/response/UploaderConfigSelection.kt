package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploaderConfigSelection(
    @SerialName("cccd")
    val cccd: Boolean,
    @SerialName("cmnd")
    val cmnd: Boolean,
    @SerialName("icao_cccd")
    val icaoCCCD: Boolean,
    @SerialName("show_intro")
    val showIntro: Boolean? = null,
    @SerialName("show_video_confirmation")
    val showVideoConfirmation: Boolean? = null,
    @SerialName("record_audio")
    val recordAudio: Boolean? = null
)
