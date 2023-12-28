package com.cccd.io.sdk.capture.ui.components.gnb

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TransparentOvalLayout(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    offsetY: Dp,
    color: Color,
    dashed: Boolean,
    success: Boolean
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

            drawOval(
                topLeft = Offset(
                    x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                ),
                size = Size(widthInPx, heightInPx),
                color = Color.Transparent,
                blendMode = BlendMode.Clear,
            )
            restoreToCount(checkPoint)
        }
    }
    if (success) {
        Canvas(modifier = modifier) {
            val canvasWidth = size.width

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRect(Color.Transparent)

                drawOval(
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    ),
                    size = Size(widthInPx, heightInPx),
                    color = Color(0x72FFFFFF),
                    blendMode = BlendMode.Color,
                )
                restoreToCount(checkPoint)
            }
        }
    }


    Canvas(modifier = modifier) {
        val canvasWidth = size.width

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            drawRect(Color.Transparent)

            drawOval(
                topLeft = Offset(
                    x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                ),
                size = Size(widthInPx, heightInPx),
                color = color,
                blendMode = BlendMode.Color,
                style = Stroke(
                    width = 4.dp.toPx(), pathEffect = if (dashed) PathEffect.dashPathEffect(
                        floatArrayOf(50f, 20f), 0f
                    ) else null
                )
            )
            restoreToCount(checkPoint)
        }
    }
}