package com.cccd.io.sdk.capture.repositories.face_detection

import android.graphics.PointF
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark

class FaceDetectionRepositoryImpl : FaceDetectionRepository {

    private val defaultElementFaceDetection: ElementFacePosition = ElementFacePosition(
        leftCheek = PointF(0f, 0f),
        rightCheek = PointF(0f, 0f),
        leftEye = PointF(0f, 0f),
        rightEye = PointF(0f, 0f),
        noseBase = PointF(0f, 0f),
        mouthBottom = PointF(0f, 0f),
        mouthLeft = PointF(0f, 0f),
        mouthRight = PointF(0f, 0f)
    )

    override fun getElementFacePosition(
        face: Face,
        startPercentage: Float,
        topPercentage: Float
    ): ElementFacePosition {
        val elementFacePosition: ElementFacePosition = defaultElementFaceDetection

        val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
        leftEye?.let {
            val leftEyePos = leftEye.position
            elementFacePosition.leftEye =
                PointF(leftEyePos.x * startPercentage, leftEyePos.y * topPercentage)
        }

        val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
        rightEye?.let {
            val rightEyePos = rightEye.position
            elementFacePosition.rightEye =
                PointF(rightEyePos.x * startPercentage, rightEyePos.y * topPercentage)
        }

        val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)
        rightCheek?.let {
            val rightCheekPos = rightCheek.position
            elementFacePosition.rightCheek =
                PointF(rightCheekPos.x * startPercentage, rightCheekPos.y * topPercentage)
        }

        val leftCheck = face.getLandmark(FaceLandmark.LEFT_CHEEK)
        leftCheck?.let {
            val leftCheckPos = leftCheck.position
            elementFacePosition.leftCheek =
                PointF(leftCheckPos.x * startPercentage, leftCheckPos.y * topPercentage)
        }

        val mouthRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT)
        mouthRight?.let {
            val mouthRightPos = mouthRight.position
            elementFacePosition.mouthRight =
                PointF(mouthRightPos.x * startPercentage, mouthRightPos.y * topPercentage)
        }

        val mouthLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT)
        mouthLeft?.let {
            val mouthLeftPos = mouthLeft.position
            elementFacePosition.mouthLeft =
                PointF(mouthLeftPos.x * startPercentage, mouthLeftPos.y * topPercentage)
        }

        val mouthBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)
        mouthBottom?.let {
            val mouthBottomPos = mouthBottom.position
            elementFacePosition.mouthBottom =
                PointF(mouthBottomPos.x * startPercentage, mouthBottomPos.y * topPercentage)
        }

        val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)
        noseBase?.let {
            val noseBasePos = noseBase.position
            elementFacePosition.mouthBottom =
                PointF(noseBasePos.x * startPercentage, noseBasePos.y * topPercentage)
        }

        return elementFacePosition
    }

    override fun getFaceDetectorOptions(): FaceDetectorOptions {
        val landmarkMode = FaceDetectorOptions.LANDMARK_MODE_ALL
        val classificationMode = FaceDetectorOptions.CLASSIFICATION_MODE_ALL
        val performanceMode = FaceDetectorOptions.PERFORMANCE_MODE_FAST

        val minFaceSize = "0.1".toFloat()
        val optionsBuilder = FaceDetectorOptions.Builder()
            .setLandmarkMode(landmarkMode)
            .setClassificationMode(classificationMode)
            .setPerformanceMode(performanceMode)
            .setMinFaceSize(minFaceSize)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)

        return optionsBuilder.build()
    }

}