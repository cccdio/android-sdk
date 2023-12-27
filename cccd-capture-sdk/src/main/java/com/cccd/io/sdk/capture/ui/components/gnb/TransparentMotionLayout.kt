package com.cccd.io.sdk.capture.ui.components.gnb

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun DrawScope.drawStartBorderCanvas(
    width: Float,
    height: Float,
    topLeft: Offset,
    borderColor: Color = Color.White,
    curvePx: Float,
    strokeWidth: Dp,
    capSize: Dp,
    cap: StrokeCap = StrokeCap.Square,
    lineCap: StrokeCap = StrokeCap.Round,
) {
    val mCapSize = capSize.toPx()

    val sweepAngle = 45f

    strokeWidth.toPx().toInt()

    val mCurve = curvePx * 2

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 0f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, height - mCurve + topLeft.y
        )
    )
    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 90 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )
    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 180 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 270 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, topLeft.y
        )
    )


    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 360 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, topLeft.y
        )
    )


    drawLine(
        SolidColor(borderColor),
        Offset(width + topLeft.x, height - mCapSize + topLeft.y),
        Offset(width + topLeft.x, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(width - mCapSize + topLeft.x, height + topLeft.y),
        Offset(width - curvePx + topLeft.x, height + topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(mCapSize + topLeft.x, height + topLeft.y),
        Offset(curvePx + topLeft.x, height + topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x, height - curvePx + topLeft.y),
        Offset(topLeft.x, height - mCapSize + topLeft.y),
        strokeWidth.toPx(),
        lineCap
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x, curvePx + topLeft.y),
        Offset(topLeft.x, mCapSize + topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(curvePx + topLeft.x, +topLeft.y),
        Offset(mCapSize + topLeft.x, +topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(width - curvePx + topLeft.x, +topLeft.y),
        Offset(width - mCapSize + topLeft.x, topLeft.y),
        strokeWidth.toPx(),
        lineCap,
    )

    drawLine(
        SolidColor(borderColor),
        Offset(width + topLeft.x, curvePx + topLeft.y),
        Offset(width + topLeft.x, mCapSize + topLeft.y),
        strokeWidth.toPx(),
        lineCap
    )

}

private fun DrawScope.drawProgressLeftBorderCanvas(
    height: Float,
    topLeft: Offset,
    borderColor: Color = Color.White,
    curvePx: Float,
    strokeWidth: Dp,
    cap: StrokeCap = StrokeCap.Square,
) {

    val sweepAngle = 45f

    strokeWidth.toPx().toInt()

    val mCurve = curvePx * 2

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )
    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 180 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 270 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, topLeft.y
        )
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x, curvePx + topLeft.y),
        Offset(topLeft.x, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
    )
}

private fun DrawScope.drawProgressRightBorderCanvas(
    width: Float,
    height: Float,
    topLeft: Offset,
    borderColor: Color = Color.White,
    curvePx: Float,
    strokeWidth: Dp,
    cap: StrokeCap = StrokeCap.Square,
) {

    val sweepAngle = 45f

    strokeWidth.toPx().toInt()

    val mCurve = curvePx * 2

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 0f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, height - mCurve + topLeft.y
        )
    )
    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 90 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, height - mCurve + topLeft.y
        )
    )


    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, topLeft.y
        )
    )

    drawArc(
        color = borderColor,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 360 - sweepAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, topLeft.y
        )
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x + width, curvePx + topLeft.y),
        Offset(topLeft.x + width, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
    )
}

@Composable
fun TransparentMotionLayout(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    offsetY: Dp,
    cornerRadius: Float,
    start: Boolean,
    progress: Boolean,
    finished: Boolean,
    turnLeft: Boolean,
    turnRight: Boolean
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
                    x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                ),
                size = Size(widthInPx, heightInPx),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                color = Color.Transparent,
                blendMode = BlendMode.Clear
            )
            restoreToCount(checkPoint)
        }
    }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            drawRect(Color.Transparent)

            if (start) {
                drawStartBorderCanvas(
                    width = widthInPx,
                    height = heightInPx,
                    curvePx = cornerRadius,
                    strokeWidth = 4.dp,
                    capSize = 62.dp,
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    )
                )
            }

            if (progress && !finished) {
                drawProgressLeftBorderCanvas(
                    height = heightInPx,
                    curvePx = cornerRadius,
                    strokeWidth = 4.dp,
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    ),
                    borderColor = if (turnLeft) Color(
                        0xFF4CAF50
                    ) else Color.White
                )
                drawProgressRightBorderCanvas(
                    width = widthInPx,
                    height = heightInPx,
                    curvePx = cornerRadius,
                    strokeWidth = 4.dp,
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    ),
                    borderColor = if (turnRight) Color(
                        0xFF4CAF50
                    ) else Color.White
                )
            }

            if (finished) {
                drawRoundRect(
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2,
                        y = offsetInPx
                    ),
                    size = Size(widthInPx, heightInPx),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    color = Color(
                        0xFF4CAF50
                    ),
                    blendMode = BlendMode.Color,
                    style = Stroke(
                        width = 4.dp.toPx(),
                    )
                )
            }

            restoreToCount(checkPoint)
        }
    }
}