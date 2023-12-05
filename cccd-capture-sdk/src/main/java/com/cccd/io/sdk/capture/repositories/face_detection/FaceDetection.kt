package com.cccd.io.sdk.capture.repositories.face_detection

import android.graphics.PointF

data class FaceMeshBoundingBox(
    val height: Float,
    val width: Float,
    val offsetX: Float,
    val offsetY: Float
)

data class ElementFacePosition(
    var rightEye: PointF,
    var leftEye: PointF,
    var rightCheek: PointF,
    var leftCheek: PointF,
    var noseBase: PointF,
    var mouthRight: PointF,
    var mouthLeft: PointF,
    var mouthBottom: PointF
)
