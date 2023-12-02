package com.cccd.io.sdk.capture

import android.app.Activity

interface CCCD {
    fun startActivityForResult(
        activity: Activity,
        requestCode: Int,
        cccdConfig: CCCDConfig
    )
}
