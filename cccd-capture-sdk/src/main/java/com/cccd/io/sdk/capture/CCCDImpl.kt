package com.cccd.io.sdk.capture

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandler
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService
import com.cccd.io.sdk.capture.ui.MainActivity

class CCCDImpl : CCCD {
    override fun startActivityForResult(
        activity: Activity,
        cccdConfig: CCCDConfig
    ) {
        val navigate =
            Intent(activity, MainActivity::class.java)

        CCCDTokenExpirationHandlerService.TOKEN = cccdConfig.token
        CCCDTokenExpirationHandlerService.WORKFLOW_VERSION_ID = cccdConfig.workflowId
        startActivity(activity, navigate, null)
    }

    override fun handleActivityResult(result: CCCDResultListenerHandler) {
        CCCDResultListenerHandlerService.resultListenerHandler = result
    }
}