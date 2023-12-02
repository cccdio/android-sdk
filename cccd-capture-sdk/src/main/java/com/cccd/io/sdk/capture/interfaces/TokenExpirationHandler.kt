package com.cccd.io.sdk.capture.interfaces

interface TokenExpirationHandler {
    fun refreshToken(injectNewToken: (String?) -> Unit)
}