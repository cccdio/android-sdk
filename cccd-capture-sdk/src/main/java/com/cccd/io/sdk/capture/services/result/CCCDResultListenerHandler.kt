package com.cccd.io.sdk.capture.services.result

import com.cccd.io.sdk.capture.enums.CCCDException

interface CCCDResultListenerHandler {
    fun userCompleted()
    fun onException(exception: CCCDException)

}