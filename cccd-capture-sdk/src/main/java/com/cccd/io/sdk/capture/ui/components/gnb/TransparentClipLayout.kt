package com.cccd.io.sdk.capture.ui.components.gnb

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun TransparentClipLayout(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    offsetY: Dp,
    cornerRadius: Float = 30f
) {
    val offsetInPx: Float
    val widthInPx: Float
    val heightInPx: Float

    with(LocalDensity.current) {
        offsetInPx = offsetY.toPx()
        widthInPx = width.toPx()
        heightInPx = height.toPx()
    }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            drawRect(Color(0xDA000000))

            drawRoundRect(
                topLeft = Offset(
                    x = (canvasWidth - widthInPx) / 2,
                    y = offsetInPx
                ),
                size = Size(widthInPx, heightInPx),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                color = Color.Transparent,
                blendMode = BlendMode.Clear
            )
            restoreToCount(checkPoint)
        }
    }
}