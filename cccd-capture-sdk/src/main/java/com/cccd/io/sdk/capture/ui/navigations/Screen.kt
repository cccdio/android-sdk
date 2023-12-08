package com.cccd.io.sdk.capture.ui.navigations

sealed class Screen(val route: String) {
    object UploadDocumentPhotoScreen : Screen(route = "upload_document_photo_screen")
    object UploadDocumentPhotoCaptureScreen : Screen(route = "upload_document_photo_capture_screen")
    object UploadDocumentPhotoSubmittedScreen :
        Screen(route = "upload_document_photo_submitted_screen")

    object UploadFaceMotionScreen : Screen(route = "upload_face_motion_screen")
    object UploadFaceMotionRecorderScreen : Screen(route = "upload_face_motion_recorder_screen")

    object UploadFaceVideoScreen : Screen(route = "upload_face_video_screen")
    object UploadFaceVideoRecorderScreen : Screen(route = "upload_face_video_recorder_screen")
    object UploadFaceVideoSubmittedScreen :
        Screen(route = "upload_face_video_submitted_screen")

    object UploadFacePhotoScreen : Screen(route = "upload_face_photo_screen")
    object UploadFacePhotoCaptureScreen : Screen(route = "upload_face_photo_capture_screen")
    object UploadFacePhotoSubmittedScreen : Screen(route = "upload_face_photo_submitted_screen")

    object RecordBiometricsVideoCameraErrorScreen :
        Screen(route = "record_biometrics_videoCamera_error_screen")


    object VerificationCompleteScreen : Screen(route = "verification_complete_screen")

    object FlashScreen : Screen(route = "flash_screen")
}