package com.cccd.io.sdk.capture.repositories.face_detection

import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetectorOptions

interface FaceDetectionRepository {
    fun getElementFacePosition(
        face: Face,
        startPercentage: Float,
        topPercentage: Float
    ): ElementFacePosition

    fun getFaceDetectorOptions(): FaceDetectorOptions

    companion object {
        fun create(): FaceDetectionRepository {
            return FaceDetectionRepositoryImpl()
        }
    }
}