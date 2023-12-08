package com.cccd.io.sdk.capture.enums

enum class FlowStep(val value: String) {
    UPLOAD_DOCUMENT_PHOTO("upload_document_photo"), UPLOAD_FACE_PHOTO("upload_face_photo"), UPLOAD_FACE_VIDEO(
        "upload_face_video"
    ),
    UPLOAD_FACE_MOTION("upload_face_motion")
}