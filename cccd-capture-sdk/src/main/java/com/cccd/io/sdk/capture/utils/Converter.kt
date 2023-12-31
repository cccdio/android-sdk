package com.cccd.io.sdk.capture.utils

import android.content.Context
import android.util.DisplayMetrics

class Converter {
    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Int {
            return ((dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt())
        }
    }
}