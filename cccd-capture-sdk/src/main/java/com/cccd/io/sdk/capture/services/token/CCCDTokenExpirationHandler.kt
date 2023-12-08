package com.cccd.io.sdk.capture.services.token

interface CCCDTokenExpirationHandler {
    fun refreshToken(injectNewToken: (String?) -> Unit)
}