package com.cccd.io.sdk.capture

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.cccd.io.sdk.capture.ui.MainActivity

class CCCDImpl(appContext: Context) : CCCD {
    private val appContext: Context = appContext
    override fun startActivityForResult(
        activity: Activity,
        requestCode: Int,
        cccdConfig: CCCDConfig
    ) {
        val navigate =
            Intent(activity, MainActivity::class.java)
        startActivity(activity, navigate, null)
    }
}