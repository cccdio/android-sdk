package com.cccd.io.sdk.capture.ui.navigations

sealed class Screen(val route: String) {
    object DocumentCaptureScreen : Screen(route = "document_capture_screen")
    object RecordBiometricsMotionScreen : Screen(route = "record_biometrics_motion_screen")
    object RecordBiometricsVideoCameraErrorScreen :
        Screen(route = "record_biometrics_videoCamera_error_screen")

    object RecordBiometricsVideoCameraSubmitScreen :
        Screen(route = "record_biometrics_video_camera_submit_screen")

    object VerificationCompleteScreen : Screen(route = "verification_complete_screen")
    object ScanDocumentScreen : Screen(route = "scan_document_screen")
    object ScanDocumentSubmittedScreen : Screen(route = "scan_document_submitted_screen")
    object BiometricsMotionScreen : Screen(route = "biometrics_motion_screen")
}