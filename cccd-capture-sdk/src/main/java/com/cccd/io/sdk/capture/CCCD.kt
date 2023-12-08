package com.cccd.io.sdk.capture

import android.app.Activity
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandler

interface CCCD {
    fun startActivityForResult(
        activity: Activity,
        cccdConfig: CCCDConfig
    )

    fun handleActivityResult(result: CCCDResultListenerHandler)
}
