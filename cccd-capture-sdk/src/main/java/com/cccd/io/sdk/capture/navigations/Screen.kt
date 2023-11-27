package com.cccd.io.sdk.capture.navigations

sealed class Screen(val route: String) {
    object DocumentCaptureScreen : Screen(route = "document_capture_screen")
}