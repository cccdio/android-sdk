package com.cccd.io.sdk.capture

class CCCDFactory {
    val client: CCCD = CCCDImpl()

    companion object {
        fun create(): CCCDFactory {
            return CCCDFactory()
        }
    }
}