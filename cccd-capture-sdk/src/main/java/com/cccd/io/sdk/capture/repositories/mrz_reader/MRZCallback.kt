package com.cccd.io.sdk.capture.repositories.mrz_reader

import org.jmrtd.lds.icao.MRZInfo

interface MRZCallback {
    fun onMRZRead(mrzInfo: MRZInfo)
    fun onMRZReadFailure()
    fun onFailure(e: Exception)
}