package com.cccd.io.sdk.capture.components.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.R
import com.cccd.io.sdk.capture.components.SVGLoader


@Composable
fun CareLeftIcon() {
    Box {
        SVGLoader(model = R.drawable.ic_care_left, modifier = Modifier.size(24.dp))
    }
}

