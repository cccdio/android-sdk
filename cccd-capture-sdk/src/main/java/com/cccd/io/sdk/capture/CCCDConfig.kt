package com.cccd.io.sdk.capture

import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandler
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService

open class CCCDConfig private constructor(
    token: String,
    workflowId: String,
) {
    val token: String = token
    val workflowId: String = workflowId

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private var workflowId: String = ""
        private var token: String = ""


        fun withSDKToken(
            sdkToken: String,
            workflowId: String,
            tokenExpirationHandler: CCCDTokenExpirationHandler?
        ): Builder {
            token = sdkToken
            this.workflowId = workflowId
            CCCDTokenExpirationHandlerService.tokenExpirationHandler = tokenExpirationHandler

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