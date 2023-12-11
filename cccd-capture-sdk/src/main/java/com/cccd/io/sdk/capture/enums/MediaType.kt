package com.cccd.io.sdk.capture.enums

enum class MediaType(val value: String) {
    FACE_VIDEO("face_video"),
    FACE_MOTION("face_motion"),
    FACE_PHOTO("face_photo"),
    CCCD_DOCUMENT_PHOTO_FRONT("cccd_document_photo_front"),
    CCCD_DOCUMENT_PHOTO_BACK("cccd_document_photo_back"),
    CMND_DOCUMENT_PHOTO_FRONT("cmnd_document_photo_front"),
    CMND_DOCUMENT_PHOTO_BACK("cmnd_document_photo_back"),
    OLD_CCCD_DOCUMENT_PHOTO_FRONT("old_cccd_document_photo_front"),
    OLD_CCCD_DOCUMENT_PHOTO_BACK("old_cccd_document_photo_back"),
}