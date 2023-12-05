package com.cccd.io.sdk.capture

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.cccd.io.sdk.capture.ui.MainActivity

class CCCDImpl(private val appContext: Context) : CCCD {
    override fun startActivityForResult(
        activity: Activity,
        requestCode: Int,
        cccdConfig: CCCDConfig
    ) {
        val navigate =
            Intent(activity, MainActivity::class.java)

        navigate.putExtra("config", cccdConfig)
        startActivity(activity, navigate, null)
    }
}