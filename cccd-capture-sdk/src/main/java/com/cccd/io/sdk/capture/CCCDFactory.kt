package com.cccd.io.sdk.capture

import android.content.Context

class CCCDFactory private constructor(appContext: Context) {
    val client: CCCD = CCCDImpl(appContext)

    companion object {
        fun create(context: Context): CCCDFactory {
            return CCCDFactory(context)
        }
    }
}