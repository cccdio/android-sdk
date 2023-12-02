package com.cccd.io.sdk.capture.api.client.token.sdk.url

import java.io.Serializable

interface ApiUrlCreator : Serializable {
    fun createApiUrl(token: String): String
}