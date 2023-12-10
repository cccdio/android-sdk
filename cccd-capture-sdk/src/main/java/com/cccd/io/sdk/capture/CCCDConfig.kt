package com.cccd.io.sdk.capture

import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandler
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService

open class CCCDConfig private constructor(
    token: String,
    workflowRunId: String,
) {
    val token: String = token
    val workflowRunId: String = workflowRunId

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private var workflowRunId: String = ""
        private var token: String = ""


        fun withSDKToken(
            sdkToken: String,
            tokenExpirationHandler: CCCDTokenExpirationHandler?
        ): Builder {
            token = sdkToken
            CCCDTokenExpirationHandlerService.tokenExpirationHandler = tokenExpirationHandler

            return this
        }

        fun withWorkflowRunId(workflowRunId: String): Builder {
            this.workflowRunId = workflowRunId

            return this
        }

        fun build(): CCCDConfig {
            return CCCDConfig(
                token,
                workflowRunId,
            )
        }
    }
}