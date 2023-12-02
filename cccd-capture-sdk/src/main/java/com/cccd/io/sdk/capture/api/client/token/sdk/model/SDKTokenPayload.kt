package com.cccd.io.sdk.capture.api.client.token.sdk.model

import com.cccd.io.sdk.capture.api.client.getJsonParserInstance
import com.cccd.io.sdk.capture.api.client.token.sdk.SDKTokenExtractor
import kotlinx.serialization.SerialName
import java.io.Serializable


@kotlinx.serialization.Serializable
data class SDKTokenPayload(
    @SerialName("urls")
    private val urls: SDKTokenUrl? = null,

    @SerialName("uuid")
    val uuid: String? = null,

    @SerialName("payload")
    private val payload: SDKTokenPayloadField,
) : Serializable {
    val baseUrl: String?
        get() = urls?.baseUrl
    val authUrl: String?
        get() = urls?.authUrl
    val applicantUuid: String
        get() = payload.applicantId
    val clientUuid: String
        get() = payload.clientUuid

    companion object {
        private val jsonParser = getJsonParserInstance()

        @JvmStatic
        fun parseSDKTokenPayload(sdkToken: String?): SDKTokenPayload? {
            return if (!sdkToken.isNullOrBlank()) {
                val payload = SDKTokenExtractor.decodePayload(sdkToken) ?: return null
                jsonParser.decodeFromString<SDKTokenPayload>(payload)
            } else {
                null
            }
        }
    }
}