package com.cccd.io.sdk.capture.ui.components.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.R
import com.cccd.io.sdk.capture.ui.components.SVGLoader
import com.cccd.io.sdk.capture.ui.theme.theme_border_default
import com.cccd.io.sdk.capture.ui.theme.theme_border_success


@Composable
fun CareLeftIcon() {
    Box {
        SVGLoader(model = R.drawable.ic_care_left, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun CareLeftDarkIcon() {
    Box {
        SVGLoader(model = R.drawable.careleftdark, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun NoteBookIcon() {
    Box {
        SVGLoader(model = R.drawable.notebook, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun CardProfileIcon() {
    Box {
        SVGLoader(model = R.drawable.cardprofile, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun IdentificationCard() {
    Box {
        SVGLoader(model = R.drawable.identificationcard, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun DotOutline() {
    Box {
        SVGLoader(model = R.drawable.dotoutline, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun WarningCircleIcon() {
    Box {
        SVGLoader(model = R.drawable.warningcircle, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun RedWarningCircleIcon() {
    Box {
        SVGLoader(model = R.drawable.redwarningcircle, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun IllustrationIcon() {
    Box {
        SVGLoader(model = R.drawable.illustration, modifier = Modifier.size(180.dp))
    }
}

@Composable
fun VideoIcon() {
    Box {
        SVGLoader(model = R.drawable.video, modifier = Modifier.size(32.dp))
    }
}

@Composable
fun XCircleIcon() {
    Box {
        SVGLoader(model = R.drawable.xcircle, modifier = Modifier.size(60.dp))
    }
}

@Composable
fun SunDimIcon() {
    Box {
        SVGLoader(model = R.drawable.sundim, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun EyeIcon() {
    Box {
        SVGLoader(model = R.drawable.eye, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun FaceMaskIcon() {
    Box {
        SVGLoader(model = R.drawable.facemask, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun SmileyIcon() {
    Box {
        SVGLoader(model = R.drawable.smiley, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun RecordIcon() {
    Box {
        SVGLoader(model = R.drawable.record, modifier = Modifier.size(68.dp))
    }
}

@Composable
fun TopLeftRadiusIcon() {
    Box {
        SVGLoader(model = R.drawable.top_left, modifier = Modifier.size(35.dp))
    }
}

@Composable
fun TopRightRadiusIcon() {
    Box {
        SVGLoader(model = R.drawable.top_right, modifier = Modifier.size(35.dp))
    }
}

@Composable
fun BottomLeftRadiusIcon() {
    Box {
        SVGLoader(model = R.drawable.bot_left, modifier = Modifier.size(35.dp))
    }
}

@Composable
fun BottomRightRadiusIcon() {
    Box {
        SVGLoader(model = R.drawable.bot_right, modifier = Modifier.size(35.dp))
    }
}

@Composable
fun FaceFocusRight(modifier: Modifier = Modifier, colorFilter: ColorFilter? = null) {
    Box {
        SVGLoader(model = R.drawable.facefocusright, modifier = modifier, colorFilter = colorFilter)
    }
}

@Composable
fun FaceFocusLeft(modifier: Modifier = Modifier, colorFilter: ColorFilter? = null) {
    Box {
        SVGLoader(model = R.drawable.facefocusleft, modifier = modifier, colorFilter = colorFilter)
    }
}

@Composable
fun SuccessCheckIcon() {
    Box {
        SVGLoader(
            model = R.drawable.icon_check_success,
            modifier = Modifier.size(45.dp)
        )
    }
}

@Composable
fun UserIcon() {
    Box {
        SVGLoader(
            model = R.drawable.user,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun EyeGlassesIcon() {
    Box {
        SVGLoader(
            model = R.drawable.eyeglasses,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ArrowCounterClockWiseIcon() {
    Box {
        SVGLoader(
            model = R.drawable.arrowcounterclockwise,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ArrowLeftIcon(valueFilter: Float) {
    Box {
        SVGLoader(
            model = R.drawable.arrowleft,
            modifier = Modifier
                .width(76.dp)
                .height(33.dp)
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    val brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.0f to theme_border_default,
                            (1 - valueFilter) to theme_border_default,
                            (1 - valueFilter) to theme_border_success,
                            1f to theme_border_success
                        )
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                },
        )
    }
}

@Composable
fun ArrowRightIcon(valueFilter: Float) {
    Box {
        SVGLoader(
            model = R.drawable.arrowright,
            modifier = Modifier
                .width(76.dp)
                .height(33.dp)
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    val brush = Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.0f to theme_border_success,
                            valueFilter to theme_border_success,
                            valueFilter to theme_border_default,
                            1f to theme_border_default
                        )
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                },
        )
    }
}

@Composable
fun NFCIcon() {
    Box {
        SVGLoader(
            model = R.drawable.nfc,
            modifier = Modifier
                .width(113.dp)
                .height(161.dp)
        )
    }
}


