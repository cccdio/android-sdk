package com.cccd.io.sdk.capture

import android.content.Context

open class CCCDConfig private constructor() {

    companion object {
        fun builder(context: Context): Builder {
            return Builder(context)
        }
    }

    class Builder constructor(context: Context) {
        private var apiCertificatePinningPKHashes: Array<String>? = arrayOf()

        private var baseUrl: String? = null

        private val context: Context = context


//        private var documentTypes: List<com.onfido.android.sdk.capture.DocumentType> /* compiled code */
//
//        private var exitWhenSentToBackground: Boolean /* compiled code */
//
//        private var flowStepsWithOptions: Array<com.onfido.android.sdk.capture.ui.options.FlowStep>? /* compiled code */
//
//        private var genericDocuments: List<com.onfido.android.sdk.capture.document.GenericDocument> /* compiled code */
//
//        private var locale: java.util.Locale? /* compiled code */
//
//        private var manualLivenessCapture: Boolean /* compiled code */
//
//        private var mediaCallback: android.os.ResultReceiver? /* compiled code */
//
//        private var nfcOptions: com.onfido.android.sdk.capture.model.NFCOptions /* compiled code */
//
//        private var onfidoAnalyticsEventListener: android.os.ResultReceiver? /* compiled code */
//
//        private var requestedEnterpriseFeatures: com.onfido.android.sdk.capture.EnterpriseFeatures? /* compiled code */
//
//        private var theme: com.onfido.android.sdk.capture.OnfidoTheme /* compiled code */
//
//        private lateinit var token: com.onfido.api.client.token.Token /* compiled code */
//
//        private var tokenExpirationHandlerEnabled: Boolean /* compiled code */
//
//        private var workflowConfig: com.onfido.android.sdk.FlowConfig? /* compiled code */


        fun withSDKToken(sdkToken: String): Builder {
            return this
        }

        fun withCustomerFlow(): Builder {
            return this
        }

        fun build(): CCCDConfig {
            return CCCDConfig()
        }
    }
}