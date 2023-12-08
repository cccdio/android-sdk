package com.cccd.io.sdk.capture.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder


@Composable
fun SVGLoader(
    model: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter : ColorFilter? = null
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        add(SvgDecoder.Factory())
    }.build()

    Image(
        painter = rememberAsyncImagePainter(model = model, imageLoader),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}