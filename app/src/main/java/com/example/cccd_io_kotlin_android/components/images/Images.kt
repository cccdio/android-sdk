package com.example.cccd_io_kotlin_android.components.images

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.cccd_io_kotlin_android.R
import com.example.cccd_io_kotlin_android.components.SVGLoader

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