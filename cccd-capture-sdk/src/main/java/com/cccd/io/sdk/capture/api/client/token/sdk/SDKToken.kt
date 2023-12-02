package com.cccd.io.sdk.capture.api.client.token.sdk

import com.cccd.io.sdk.capture.api.client.exception.EnterpriseFeaturesNotAuthorizedException
import com.cccd.io.sdk.capture.api.client.getJsonParserInstance
import com.cccd.io.sdk.capture.api.client.token.Token
import com.cccd.io.sdk.capture.api.client.token.sdk.model.SDKTokenPayload
import com.cccd.io.sdk.capture.api.client.token.sdk.url.ApiUrlCreator
import com.cccd.io.sdk.capture.api.client.token.sdk.url.ApiUrlCreatorImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class SDKToken @JvmOverloads constructor(
    tokenValue: String,
    appId: String?,
    urlCreator: ApiUrlCreator = ApiUrlCreatorImpl()
) : Token(tokenValue) {
    private val urlCreator: ApiUrlCreator
    private var sdkTokenPayload: SDKTokenPayload? = null

    @Transient
    private val jsonParser: Json = getJsonParserInstance()

    val enterpriseFeatures: Map<String, JsonElement>
        get() {
            val json = SDKTokenExtractor.decodePayload(tokenValue)
            val tokenMap = json?.let {
                jsonParser
                    .decodeFromString<Map<String, JsonElement>>(it)
            }

            if (tokenMap != null) {
                if (!tokenMap.containsKey(ENTERPRISE_FEATURES)) {
                    throw EnterpriseFeaturesNotAuthorizedException()
                }
            }
            return tokenMap?.get(ENTERPRISE_FEATURES)?.jsonObject?.toMap() ?: emptyMap()
        }
    val uuid: String?
        get() {
            val sdkTokenPayload = ensureSDKTokenPayload()
            return sdkTokenPayload?.uuid
        }

    val applicantUuid: String
        get() {
            if (isDemoToken) {
                return tokenValue // Just return tokenValue which will be "demo" always
            }
            val sdkTokenPayload = ensureSDKTokenPayload()
            return sdkTokenPayload?.applicantUuid ?: ""
        }

    init {
        this.appId = appId
        this.urlCreator = urlCreator
    }

    override fun buildUrl(): String {
        return urlCreator.createApiUrl(tokenValue)!!
    }

    @Synchronized
    private fun ensureSDKTokenPayload(): SDKTokenPayload? {
        if (sdkTokenPayload == null) {
            sdkTokenPayload = SDKTokenPayload.parseSDKTokenPayload(tokenValue)
        }
        return sdkTokenPayload
    }

    companion object {
        private const val ENTERPRISE_FEATURES = "enterprise_features"
    }
}