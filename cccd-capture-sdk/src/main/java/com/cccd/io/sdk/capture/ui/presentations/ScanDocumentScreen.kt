package com.cccd.io.sdk.capture.ui.presentations

import android.content.Context
import android.content.ContextWrapper
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.gnb.TransparentClipLayout
import com.cccd.io.sdk.capture.ui.components.icons.RecordIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen
import com.cccd.io.sdk.capture.ui.repositories.ImageResize
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }


@Composable
fun ScanDocumentScreen(mainViewModel: MainActivityViewModel) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val configuration = LocalConfiguration.current
    var taken by remember {
        mutableStateOf(false)
    }

    val screenHeightInPx = mainViewModel.convertDpToPixel(configuration.screenHeightDp.dp.value)
    val screenWidthInPx = mainViewModel.convertDpToPixel(configuration.screenWidthDp.dp.value)
    val clipWidth = 350.dp
    val clipHeight = 200.dp
    val offsetClipHeight = 214.dp
    val clipWidthInPx = mainViewModel.convertDpToPixel(clipWidth.value)
    val clipHeightInPx = mainViewModel.convertDpToPixel(clipHeight.value)
    val offsetClipHeightInPx = mainViewModel.convertDpToPixel(offsetClipHeight.value)

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture =
        remember {
            ImageCapture.Builder().setTargetResolution(Size(screenWidthInPx, screenHeightInPx))
                .build()
        }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(key1 = lensFacing) {
        mainViewModel.requestCameraPermission()
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    if (mainViewModel.shouldShowCamera) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

            Surface(
                color = Color.Black.copy(alpha = 0f),
                modifier = Modifier.fillMaxSize()
            ) {
                TransparentClipLayout(
                    width = clipWidth,
                    height = clipHeight,
                    offsetY = offsetClipHeight
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(title = "", onGoBack = {})
                    Column(
                        verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                start = Variables.CornerL,
                                top = 120.dp,
                                end = Variables.CornerL,
                                bottom = Variables.CornerL
                            )
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(clipWidth)
                                .height(clipHeight)
                                .padding(top = offsetClipHeight)
                        )
                        Text(
                            text = "Position the front of your document in the frame",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFFCBC5C9),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.Bottom),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                0.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(start = 4.dp, top = 32.dp, end = 4.dp, bottom = 32.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    taken = true
                                    mainViewModel.camera.takePhoto(
                                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                        imageCapture = imageCapture,
                                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                                            ContextWrapper(
                                                context
                                            )
                                        ),
                                        executor = mainViewModel.cameraExecutor,
                                        onImageCaptured = {
                                            mainViewModel.outputBitmap =
                                                mainViewModel.camera.imageCapture(
                                                    uri = it,
                                                    activity = mainViewModel.componentActivity,
                                                    ImageResize(
                                                        height = clipHeightInPx,
                                                        width = clipWidthInPx,
                                                        heightOffset = offsetClipHeightInPx,
                                                        widthOffset = ((screenWidthInPx - clipWidthInPx) / 2),
                                                        screenHeight = screenHeightInPx,
                                                        screenWidth = screenWidthInPx,
                                                    ),
                                                    onCallback = {

                                                    }
                                                )

                                        },
                                        onError = {
                                            TODO("Handle error")
                                        }
                                    )
                                    mainViewModel.captured = true

                                },
                                content = {
                                    RecordIcon()
                                },
                                modifier = Modifier
                                    .width(64.dp)
                                    .height(64.dp),
                                enabled = !taken
                            )
                        }
                    }
                }
            }
        }
    }

    if (mainViewModel.permissionDenied) {
        Column {
            Text("No camera")

        }
    }

    if (mainViewModel.outputBitmap != null && mainViewModel.captured) {
        mainViewModel.captured = false
        mainViewModel.navController.navigate(
            Screen.ScanDocumentSubmittedScreen.route
        )
    }
}
