package com.cccd.io.sdk.capture.ui.components.gnb.draws

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.cccd.io.sdk.capture.ui.theme.theme_border_default
import com.cccd.io.sdk.capture.ui.theme.theme_border_white

fun DrawScope.drawStartBorderCanvas(
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

fun DrawScope.drawProgressLeftBorderCanvas(
    width: Float,
    height: Float,
    topLeft: Offset,
    curvePx: Float,
    strokeWidth: Dp,
    cap: StrokeCap = StrokeCap.Square,
    valueFilter: Float
) {

    val sweepAngle = 45f

    strokeWidth.toPx().toInt()

    val mCurve = curvePx * 2
    val xBrush1 = if (valueFilter > 0.25) 1f else valueFilter / 0.25f
    val brush1 = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush1) to theme_border_default,
            (1 - xBrush1) to theme_border_white,
            1f to theme_border_white
        )
    )

    val xBrush2 =
        if (valueFilter > 0.5) 1f else if (valueFilter < 0.25f) 0f else ((valueFilter - 0.25f) * 0.1f / 0.25f + 0.6f)
    val brush2 = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush2) to theme_border_default,
            (1 - xBrush2) to theme_border_white,
            1f to theme_border_white
        )
    )
    val xBrush3 =
        if (valueFilter > 0.75) 1f else if (valueFilter < 0.5f) 0f else (valueFilter - 0.50f) / 0.25f

    val xBrush3Top = if (valueFilter < 0.5f) 0f else xBrush3 * 0.1f + 0.22f
    val xBrush3Bot = if (valueFilter < 0.5f) 0f else xBrush3 * 0.1f + 0.42f
    val brush3Top = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush3Top to theme_border_white,
            xBrush3Top to theme_border_default,
            1f to theme_border_default
        )
    )
    val brush3Bot = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush3Bot) to theme_border_default,
            (1 - xBrush3Bot) to theme_border_white,
            1f to theme_border_white
        )
    )

    val xBrush4 = if (valueFilter < 0.75) 0f else (valueFilter - 0.75f) / 0.25f
    val xBrush4Top = if (valueFilter < 0.75) 0f else xBrush4 * 0.08f + 0.32f
    val xBrush4Bot = if (valueFilter < 0.75) 0f else xBrush4 * 0.08f + 0.53f

    val brush4Top = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush4Top to theme_border_white,
            xBrush4Top to theme_border_default,
            1f to theme_border_default
        ), tileMode = TileMode.Repeated
    )
    val brush4Bot = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush4Bot) to theme_border_default,
            (1 - xBrush4Bot) to theme_border_white,
            1f to theme_border_white
        )
    )


    drawLine(
        brush = brush1,
        Offset(topLeft.x + curvePx, topLeft.y),
        Offset(topLeft.x + width / 2 - 5, topLeft.y),
        strokeWidth.toPx(),
    )
    drawArc(
        brush = brush2,
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
        brush = brush3Top,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 180f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, topLeft.y
        )
    )
    drawLine(
        brush = brush4Top,
        Offset(topLeft.x, curvePx + topLeft.y),
        Offset(topLeft.x, height / 2 + topLeft.y),
        strokeWidth.toPx(),
    )
    drawLine(
        brush = brush4Bot,
        Offset(topLeft.x, height / 2 + topLeft.y),
        Offset(topLeft.x, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
    )
    drawArc(
        brush = brush3Bot,
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
        brush = brush2,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawLine(
        brush = brush1,
        Offset(topLeft.x + curvePx, height + topLeft.y),
        Offset(topLeft.x + width / 2 - 5, height + topLeft.y),
        strokeWidth.toPx(),
    )
}

fun DrawScope.drawProgressRightBorderCanvas(
    width: Float,
    height: Float,
    topLeft: Offset,
    curvePx: Float,
    strokeWidth: Dp,
    cap: StrokeCap = StrokeCap.Square,
    valueFilter: Float
) {

    val sweepAngle = 45f

    strokeWidth.toPx().toInt()

    val mCurve = curvePx * 2
    val xBrush1 = if (valueFilter > 0.25) 1f else valueFilter / 0.25f
    val brush1 = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush1 to theme_border_white,
            xBrush1 to theme_border_default,
            1f to theme_border_default
        )
    )

    val xBrush2 =
        if (valueFilter > 0.5) 1f else if (valueFilter < 0.25f) 0f else ((valueFilter - 0.25f) * 0.24f / 0.25f + 0.54f)
    val brush2 = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush2 to theme_border_white,
            xBrush2 to theme_border_default,
            1f to theme_border_default
        )
    )
    val xBrush3 =
        if (valueFilter > 0.75) 1f else if (valueFilter < 0.5f) 0f else (valueFilter - 0.50f) / 0.25f

    val xBrush3Top = if (valueFilter < 0.5f) 0f else xBrush3 * 0.1f + 0.22f
    val xBrush3Bot = if (valueFilter < 0.5f) 0f else xBrush3 * 0.1f + 0.42f
    val brush3Top = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush3Top to theme_border_white,
            xBrush3Top to theme_border_default,
            1f to theme_border_default
        )
    )
    val brush3Bot = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush3Bot) to theme_border_default,
            (1 - xBrush3Bot) to theme_border_white,
            1f to theme_border_white
        )
    )

    val xBrush4 = if (valueFilter < 0.75) 0f else (valueFilter - 0.75f) / 0.25f
    val xBrush4Top = if (valueFilter < 0.75) 0f else xBrush4 * 0.08f + 0.32f
    val xBrush4Bot = if (valueFilter < 0.75) 0f else xBrush4 * 0.08f + 0.53f

    val brush4Top = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_white,
            xBrush4Top to theme_border_white,
            xBrush4Top to theme_border_default,
            1f to theme_border_default
        ), tileMode = TileMode.Repeated
    )
    val brush4Bot = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to theme_border_default,
            (1 - xBrush4Bot) to theme_border_default,
            (1 - xBrush4Bot) to theme_border_white,
            1f to theme_border_white
        )
    )

    drawLine(
        brush1,
        Offset(topLeft.x + width / 2 + 5, topLeft.y),
        Offset(topLeft.x + width - curvePx, topLeft.y),
        strokeWidth.toPx(),
    )

    drawArc(
        brush2,
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
        brush3Top,
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
        brush4Top,
        Offset(topLeft.x + width, curvePx + topLeft.y),
        Offset(topLeft.x + width, height / 2 + topLeft.y),
        strokeWidth.toPx(),
    )
    drawLine(
        brush4Bot,
        Offset(topLeft.x + width, topLeft.y + height / 2),
        Offset(topLeft.x + width, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
    )
    drawArc(
        brush2,
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
        brush3Bot,
        style = Stroke(strokeWidth.toPx(), cap = cap),
        startAngle = 0f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            width - mCurve + topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawLine(
        brush1,
        Offset(topLeft.x + width / 2 + 5, height + topLeft.y),
        Offset(topLeft.x + width - curvePx, height + topLeft.y),
        strokeWidth.toPx(),
    )
}

fun DrawScope.drawLeftBorderCircleCanvas(
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



    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x + curvePx, topLeft.y),
        Offset(topLeft.x + width / 2 - 5, topLeft.y),
        strokeWidth.toPx(),
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
        startAngle = 180f,
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
        Offset(topLeft.x, height / 2 + topLeft.y),
        strokeWidth.toPx(),
    )
    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x, height / 2 + topLeft.y),
        Offset(topLeft.x, height - curvePx + topLeft.y),
        strokeWidth.toPx(),
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
        startAngle = 90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(mCurve, mCurve),
        topLeft = Offset(
            topLeft.x, height - mCurve + topLeft.y
        )
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x + curvePx, height + topLeft.y),
        Offset(topLeft.x + width / 2 - 5, height + topLeft.y),
        strokeWidth.toPx(),
    )
}

fun DrawScope.drawRightBorderCircleCanvas(
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

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x + width / 2 + 5, topLeft.y),
        Offset(topLeft.x + width - curvePx, topLeft.y),
        strokeWidth.toPx(),
    )

    drawLine(
        SolidColor(borderColor),
        Offset(topLeft.x + width / 2 + 5, height + topLeft.y),
        Offset(topLeft.x + width - curvePx, height + topLeft.y),
        strokeWidth.toPx(),
    )
}