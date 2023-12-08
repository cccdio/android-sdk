package com.cccd.io.sdk.capture.services.result

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger

class CCCDResultListenerHandlerService : Service() {

    private val messenger: Messenger = Messenger(IncomingHandler())
    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }

    class IncomingHandler(looper: Looper = Looper.getMainLooper()) : Handler(looper)
    companion object {
        var resultListenerHandler: CCCDResultListenerHandler? = null
    }

}