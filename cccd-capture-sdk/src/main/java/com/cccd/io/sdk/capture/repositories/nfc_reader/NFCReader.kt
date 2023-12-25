package com.cccd.io.sdk.capture.repositories.nfc_reader

import android.content.Context
import android.content.Intent
import org.jmrtd.lds.icao.MRZInfo

interface NFCReader {
    fun read(context: Context, intent: Intent, mrzInfo: MRZInfo, eidCallback: EidCallback)
    
    companion object {
        fun create(): NFCReaderImpl {
            return NFCReaderImpl()
        }
    }
}