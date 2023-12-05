package com.cccd.io.sdk.capture

import android.content.Context
import com.cccd.io.sdk.capture.interfaces.TokenExpirationHandler
import com.cccd.io.sdk.capture.token.TokenExpirationHandlerService

open class CCCDConfig private constructor(
    token: String,
    workflowId: String,
) {
    val token: String = token
    val workflowId: String = workflowId

    companion object {
        fun builder(context: Context): Builder {
            return Builder(context)
        }
    }

    class Builder constructor(context: Context) {
        private var workflowId: String = ""
        private var token: String = ""


        fun withSDKToken(
            sdkToken: String,
            workflowId: String,
            tokenExpirationHandler: TokenExpirationHandler?
        ): Builder {
            token = sdkToken
            this.workflowId = workflowId
            TokenExpirationHandlerService.tokenExpirationHandler = tokenExpirationHandler

            return this
        }

        fun build(): CCCDConfig {
            return CCCDConfig(
                token,
                workflowId,
            )
        }
    }
}