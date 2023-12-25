package com.cccd.io.sdk.capture.repositories.mrz_reader

import android.graphics.Bitmap


interface MRZReaderRepository {
    fun process(
        bitmap: Bitmap, mrzCallback: MRZCallback
    )

    companion object {
        fun create(): MRZReaderRepositoryImpl {
            return MRZReaderRepositoryImpl()
        }
    }
}