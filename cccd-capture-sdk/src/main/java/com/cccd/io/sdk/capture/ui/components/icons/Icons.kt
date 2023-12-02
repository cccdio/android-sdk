package com.cccd.io.sdk.capture.ui.components.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.R
import com.cccd.io.sdk.capture.ui.components.SVGLoader


@Composable
fun CareLeftIcon() {
    Box {
        SVGLoader(model = R.drawable.ic_care_left, modifier = Modifier.size(24.dp))
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
fun IllustrationIcon() {
    Box {
        SVGLoader(model = R.drawable.illustration, modifier = Modifier.size(180.dp))
    }
}

@Composable
fun VideoIcon() {
    Box {
        SVGLoader(model = R.drawable.illustration, modifier = Modifier.size(32.dp))
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


