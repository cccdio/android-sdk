package com.cccd.io.sdk.capture.api.client.token

import java.io.Serializable

@Suppress("SerialVersionUIDInSerializableClass")
abstract class Token @JvmOverloads constructor(
    val tokenValue: String,
    var appId: String? = null
) : Serializable {
    val isDemoToken: Boolean
    var region = TokenConstants.DEFAULT_REGION
        protected set

    init {
        isDemoToken = TokenConstants.CCCD_DEMO_TOKEN == tokenValue
    }

    abstract fun buildUrl(): String
}