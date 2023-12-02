package com.cccd.io.sdk.capture.api.client.token.sdk.url

import com.cccd.io.sdk.capture.api.client.token.TokenConstants
import com.cccd.io.sdk.capture.api.client.token.sdk.model.SDKTokenPayload
import com.cccd.io.sdk.capture.api.client.util.StringUtil

class ApiUrlCreatorImpl : ApiUrlCreator {
    override fun createApiUrl(token: String): String {
        var url: String = TokenConstants.BASE_API_URL
        val sdkTokenPayload =
            SDKTokenPayload.parseSDKTokenPayload(token)

        if (sdkTokenPayload != null && StringUtil.hasCharacter(
                sdkTokenPayload.baseUrl
            )
        ) {
            url = sdkTokenPayload.baseUrl ?: ""
        }

        return url
    }
}