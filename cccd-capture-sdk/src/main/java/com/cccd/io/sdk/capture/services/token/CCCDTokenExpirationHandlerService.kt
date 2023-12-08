package com.cccd.io.sdk.capture.services.token

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger

class CCCDTokenExpirationHandlerService : Service() {
    private val messenger: Messenger = Messenger(IncomingHandler())
    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }

    class IncomingHandler(looper: Looper = Looper.getMainLooper()) : Handler(looper)

    companion object {
        var TOKEN = ""
        var WORKFLOW_VERSION_ID = ""
        var tokenExpirationHandler: CCCDTokenExpirationHandler? = null
    }
}