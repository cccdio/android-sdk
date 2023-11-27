package com.cccd.io.sdk.capture.factories

import android.content.Context
import com.cccd.io.sdk.capture.repositories.CCCD
import com.cccd.io.sdk.capture.repositories.CCCDImpl

class CCCDFactory private constructor(appContext: Context) {
    private val appContext: Context = appContext

    val client: CCCD = CCCDImpl(appContext)

    companion object {
        fun create(context: Context): CCCDFactory {
            return CCCDFactory(context)
        }
    }
}