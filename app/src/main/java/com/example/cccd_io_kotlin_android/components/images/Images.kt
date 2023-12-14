package com.example.cccd_io_kotlin_android.components.images

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.components.SVGLoader
import com.example.cccd_io_kotlin_android.R

@Composable
fun DecorationImage() {
    SVGLoader(
        model = R.drawable.decoration,
        modifier = Modifier
            .width(238.95255.dp)
            .height(243.2715.dp),
        contentScale = ContentScale.None
    )
}

@Composable
fun GuestImage() {
    SVGLoader(
        model = R.drawable.guest,
        modifier = Modifier
            .width(160.dp)
            .height(160.dp),
        contentScale = ContentScale.None
    )
}

@Composable
fun IllustrationImage() {
    SVGLoader(
        model = R.drawable.illustration,
        modifier = Modifier
            .width(179.39.dp)
            .height(178.72.dp),
        contentScale = ContentScale.None
    )
}