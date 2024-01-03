package com.cccd.io.sdk.capture.ui.components.gnb

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.components.gnb.draws.drawLeftBorderCircleCanvas
import com.cccd.io.sdk.capture.ui.components.gnb.draws.drawProgressLeftBorderCanvas
import com.cccd.io.sdk.capture.ui.components.gnb.draws.drawProgressRightBorderCanvas
import com.cccd.io.sdk.capture.ui.components.gnb.draws.drawRightBorderCircleCanvas
import com.cccd.io.sdk.capture.ui.components.gnb.draws.drawStartBorderCanvas
import com.cccd.io.sdk.capture.ui.theme.theme_border_default
import com.cccd.io.sdk.capture.ui.theme.theme_border_success


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
    turnRight: Boolean,
    enableTurnLeft: Boolean,
    enableTurnRight: Boolean,
    valueFilter: Float
) {
    val offsetInPx: Float
    val widthInPx: Float
    val heightInPx: Float

    with(LocalDensity.current) {
        offsetInPx = offsetY.toPx()
        widthInPx = width.toPx()
        heightInPx = height.toPx()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )


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

    if (start) {
        Canvas(modifier = modifier) {
            val canvasWidth = size.width

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRect(Color.Transparent)

                drawStartBorderCanvas(
                    width = widthInPx + rotation,
                    height = heightInPx + rotation,
                    curvePx = cornerRadius,
                    strokeWidth = 4.dp,
                    capSize = 62.dp,
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2 - rotation / 2,
                        y = offsetInPx - rotation / 2
                    )
                )


                restoreToCount(checkPoint)
            }
        }

    }

    if (progress && !finished) {
        Canvas(modifier = modifier) {
            val canvasWidth = size.width

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRect(Color.Transparent)
                if (turnLeft) {

                    drawLeftBorderCircleCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 6.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        borderColor = theme_border_success
                    )
                } else if (enableTurnLeft) {
                    drawProgressLeftBorderCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 5.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        valueFilter = valueFilter
                    )
                } else {
                    drawLeftBorderCircleCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 4.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        borderColor = theme_border_default
                    )
                }

                restoreToCount(checkPoint)
            }
        }
        Canvas(modifier = modifier) {
            val canvasWidth = size.width

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRect(Color.Transparent)

                if (turnRight) {
                    drawRightBorderCircleCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 6.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        borderColor = theme_border_success
                    )
                } else if (enableTurnRight) {
                    drawProgressRightBorderCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 6.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        valueFilter = valueFilter
                    )
                } else {
                    drawRightBorderCircleCanvas(
                        width = widthInPx,
                        height = heightInPx,
                        curvePx = cornerRadius,
                        strokeWidth = 4.dp,
                        topLeft = Offset(
                            x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                        ),
                        borderColor = theme_border_default
                    )
                }

                restoreToCount(checkPoint)
            }
        }
    }

    if (finished) {
        Canvas(modifier = modifier) {
            val canvasWidth = size.width

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                drawRect(Color.Transparent)
                drawRoundRect(
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    ),
                    size = Size(widthInPx, heightInPx),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    color = theme_border_success,
                    blendMode = BlendMode.Color,
                    style = Stroke(
                        width = 6.dp.toPx(),
                    )
                )
                drawRoundRect(
                    topLeft = Offset(
                        x = (canvasWidth - widthInPx) / 2, y = offsetInPx
                    ),
                    size = Size(widthInPx, heightInPx),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    color = Color.White,
                    blendMode = BlendMode.Color,
                    alpha = 0.4f
                )

                restoreToCount(checkPoint)
            }

        }
    }
}