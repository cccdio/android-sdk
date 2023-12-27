package com.cccd.io.sdk.capture.repositories.nfc_reader

import com.cccd.io.sdk.capture.models.data.Eid

interface EidCallback {
    fun onEidReadStart()
    fun onEidReadFinish()
    fun onEidRead(passport: Eid)

    fun onEidReadError(e: Exception)

}