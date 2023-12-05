package com.cccd.io.sdk.capture.ui.components.gnb

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun FaceBoxScreen(
    modifier: Modifier = Modifier,
    width: Float,
    height: Float,
    offsetX: Float,
    offsetY: Float
) {
    val paint = Paint()
    paint.strokeWidth = 6f
    paint.color = Color.Red.hashCode()
    paint.style = Paint.Style.STROKE

    Canvas(modifier = modifier) {
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

//            drawRect(Color(0xDA000000))

            drawRoundRect(
                RectF(offsetX, offsetY, offsetX + width, offsetY + height), 0f, 0f, paint
            )
            restoreToCount(checkPoint)
        }
    }
}