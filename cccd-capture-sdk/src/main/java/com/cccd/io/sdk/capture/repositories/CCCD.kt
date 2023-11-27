package com.cccd.io.sdk.capture.repositories

import android.app.Activity
import com.cccd.io.sdk.capture.CCCDConfig

interface CCCD {
    fun startActivityForResult(
        activity: Activity,
        requestCode: Int,
        cccdConfig: CCCDConfig
    )
}
