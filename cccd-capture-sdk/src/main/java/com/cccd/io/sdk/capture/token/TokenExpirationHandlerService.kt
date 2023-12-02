package com.cccd.io.sdk.capture.token

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger
import com.cccd.io.sdk.capture.interfaces.TokenExpirationHandler

class TokenExpirationHandlerService : Service() {
    private val messenger: Messenger = Messenger(IncomingHandler())
    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }

    class IncomingHandler(looper: Looper = Looper.getMainLooper()) : Handler(looper)

    companion object {
        internal const val TOKEN_KEY: String = "Token"

        var tokenExpirationHandler: TokenExpirationHandler? = null
    }
}