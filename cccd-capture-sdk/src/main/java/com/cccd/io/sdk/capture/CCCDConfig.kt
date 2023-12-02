package com.cccd.io.sdk.capture

import android.content.Context
import com.cccd.io.sdk.capture.api.client.token.Token
import com.cccd.io.sdk.capture.api.client.token.sdk.SDKToken
import com.cccd.io.sdk.capture.interfaces.TokenExpirationHandler
import com.cccd.io.sdk.capture.token.TokenExpirationHandlerService
import com.cccd.io.sdk.capture.ui.options.FlowStep
import java.util.Locale
import kotlin.jvm.internal.Intrinsics

open class CCCDConfig private constructor(
    flowSteps: Array<FlowStep>?,
    baseURL: String?,
    token: Token?,
    locale: Locale?,
    tokenExpirationHandlerEnabled: Boolean?
) {
    private val flowSteps: Array<FlowStep>? = flowSteps
    private val baseUrl: String? = baseURL
    private val token: Token? = token
    private val locale: Locale? = locale
    private val tokenExpirationHandlerEnabled = tokenExpirationHandlerEnabled

    companion object {
        fun builder(context: Context): Builder {
            return Builder(context)
        }
    }

    class Builder constructor(context: Context) {
        private var baseUrl: String? = null
        private val context: Context = context
        private var token: Token? = null
        private var flowStepsWithOptions: Array<FlowStep>? = null
        private var locale: Locale? = null
        private var tokenExpirationHandlerEnabled = false


        fun withSDKToken(
            sdkToken: String,
            tokenExpirationHandler: TokenExpirationHandler?
        ): Builder {
            // validator

            token = SDKToken(
                sdkToken,
                context.packageName,
            )
            TokenExpirationHandlerService.tokenExpirationHandler = tokenExpirationHandler

            val tokenExpirationHandlerEnabled: Boolean = tokenExpirationHandler != null

            this.tokenExpirationHandlerEnabled = tokenExpirationHandlerEnabled
            return this
        }

        fun withCustomFlow(flowSteps: Array<FlowStep>): Builder {
            Intrinsics.checkNotNullParameter(flowSteps, "steps")
            // validateFlowSteps
            flowStepsWithOptions = flowSteps
            return this
        }

        fun withBaseUrl(baseUrl: String?): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun withLocale(locale: Locale): Builder {
            Intrinsics.checkNotNullParameter(locale, "locale")
            this.locale = locale
            return this
        }

        fun build(): CCCDConfig {
            return CCCDConfig(
                flowStepsWithOptions,
                baseUrl,
                token,
                locale,
                tokenExpirationHandlerEnabled
            )
        }
    }
}