package com.cccd.io.sdk.capture.ui.presentations

import android.content.Context
import android.content.ContextWrapper
import android.media.Image
import android.net.Uri
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.cccd.io.sdk.capture.repositories.face_detection.FaceMeshBoundingBox
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBarType
import com.cccd.io.sdk.capture.ui.components.gnb.TransparentClipLayout
import com.cccd.io.sdk.capture.ui.components.icons.BottomLeftRadiusIcon
import com.cccd.io.sdk.capture.ui.components.icons.BottomRightRadiusIcon
import com.cccd.io.sdk.capture.ui.components.icons.FaceFocusLeft
import com.cccd.io.sdk.capture.ui.components.icons.FaceFocusRight
import com.cccd.io.sdk.capture.ui.components.icons.SuccessCheckIcon
import com.cccd.io.sdk.capture.ui.components.icons.TopLeftRadiusIcon
import com.cccd.io.sdk.capture.ui.components.icons.TopRightRadiusIcon
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import kotlinx.coroutines.delay
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

@OptIn(ExperimentalGetImage::class)
@Composable
fun BiometricsMotionScreen(mainViewModel: MainActivityViewModel) {
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var recording: Recording? by remember {
        mutableStateOf(null)
    }

    val configuration = LocalConfiguration.current

    var uri: Uri? by remember {
        mutableStateOf(null)
    }

    var headTurnLeft by remember {
        mutableStateOf(false)
    }

    var headTurnRight by remember {
        mutableStateOf(false)
    }

    var enableRecording by remember {
        mutableStateOf(false)
    }

    var timer by remember { mutableIntStateOf(0) }

    val screenHeightInPx = mainViewModel.convertDpToPixel(configuration.screenHeightDp.dp.value)
    val screenWidthInPx = mainViewModel.convertDpToPixel(configuration.screenWidthDp.dp.value)
    val clipWidth = 280.dp
    val clipHeight = 380.dp
    val clipWidthInPx = mainViewModel.convertDpToPixel(clipWidth.value)
    val clipHeightInPx = mainViewModel.convertDpToPixel(clipHeight.value)
    val offsetClipHeight = 137.dp
    val offsetClipHeightInPx = mainViewModel.convertDpToPixel(offsetClipHeight.value)

    val scaleX = 1.5f
    val scaleY = 2f

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()


    val recorder = Recorder.Builder().build()
    val videoCapture = remember {
        VideoCapture.withOutput(recorder)
    }

    fun getGuildMessage(): String {
        if (enableRecording) {
            if (!headTurnLeft && !headTurnRight) return "Turn your head slowly to both sides"

            if (headTurnRight && headTurnLeft) return "Recording complete"

            if (headTurnLeft)
                return "Turn your head slowly to right sides"

            return "Turn your head slowly to left sides"


        }

        return "Position your face in the frame"
    }

    fun hasFaceInBox(faceMeshBoundingBox: FaceMeshBoundingBox): Boolean {
        val leftBoxOffset = (screenWidthInPx - clipWidthInPx) / 2
        val rightBoxOffset = (screenWidthInPx + clipWidthInPx) / 2
        val bottomBoxOffset = offsetClipHeightInPx + clipHeightInPx

        if (faceMeshBoundingBox.offsetX < leftBoxOffset) return false
        if (faceMeshBoundingBox.offsetX > rightBoxOffset) return false
        if ((faceMeshBoundingBox.offsetX + faceMeshBoundingBox.width) < leftBoxOffset) return false
        if ((faceMeshBoundingBox.offsetX + faceMeshBoundingBox.width) > rightBoxOffset) return false

        if (faceMeshBoundingBox.offsetY < offsetClipHeightInPx) return false
        if (faceMeshBoundingBox.offsetY > bottomBoxOffset) return false
        if ((faceMeshBoundingBox.offsetY + faceMeshBoundingBox.height) < offsetClipHeightInPx) return false
        if ((faceMeshBoundingBox.offsetY + faceMeshBoundingBox.height) > bottomBoxOffset) return false


        return true
    }


    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            delay(1000)
            timer -= 1
        }

        if (headTurnLeft && headTurnRight) {
            if (recording != null) {
                recording?.stop()
                recording = null
            }
            return@LaunchedEffect
        }

        if (timer == 0) {
            if (recording != null) {
                recording?.stop()
                recording = null
                enableRecording = false
                headTurnLeft = false
                headTurnRight = false
            }
        }
    }

    LaunchedEffect(key1 = lensFacing) {
        mainViewModel.requestCameraPermission()
        val cameraProvider = context.getCameraProvider()


        val imageAnalysis =
            ImageAnalysis.Builder()
                .build()
        imageAnalysis.setAnalyzer(
            mainViewModel.cameraExecutor
        ) { imageProxy ->
            val mediaImage: Image? = imageProxy.image

            mediaImage?.let { it ->
                val image =
                    InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
                val detector =
                    FaceDetection.getClient(mainViewModel.faceDetection.getFaceDetectorOptions())

                detector.process(image)
                    .addOnSuccessListener { faces ->

                        val isTwoFaceInScreen = faces.size > 2

                        for (face in faces) {
                            val bounds = face.boundingBox

                            if (!headTurnLeft) {
                                headTurnLeft = face.headEulerAngleY > 40
                            }
                            if (!headTurnRight) {
                                headTurnRight = face.headEulerAngleY < -40
                            }

                            val startPointPercent = bounds.left.toFloat() / image.width
                            val topPointPercent = bounds.top.toFloat() / image.height


                            val faceWidthPercent = bounds.width().toFloat() / image.width
                            val faceHeightPercent = bounds.height().toFloat() / image.height

                            val faceMeshBoundingBox = FaceMeshBoundingBox(
                                width = screenWidthInPx * faceWidthPercent * scaleX,
                                height = screenWidthInPx * faceHeightPercent * scaleY * image.height / image.width,
                                offsetX = screenWidthInPx * startPointPercent,
                                offsetY = screenHeightInPx * topPointPercent
                            )

                            var hasOpenEyes = false
                            if (face.rightEyeOpenProbability != null && face.leftEyeOpenProbability != null) {
                                val rightEyeOpenProb = face.rightEyeOpenProbability
                                val leftEyeOpenProb = face.leftEyeOpenProbability

                                if (rightEyeOpenProb != null && leftEyeOpenProb != null) {
                                    hasOpenEyes = rightEyeOpenProb > 0.6 && leftEyeOpenProb > 0.6
                                }
                            }

                            if (!enableRecording && hasFaceInBox(faceMeshBoundingBox) && hasOpenEyes && !isTwoFaceInScreen) {
                                enableRecording = true
                                timer = 5
                                if (recording == null) {
                                    recording = mainViewModel.camera.recordVideo(
                                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                        videoCapture = videoCapture,
                                        context = context,
                                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                                            ContextWrapper(
                                                context
                                            )
                                        ),
                                        onVideoRecord = {
                                            uri = it
                                            Toast.makeText(
                                                context,
                                                it.toString(),
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        },
                                        onError = {
                                            Toast.makeText(context, it, Toast.LENGTH_LONG)
                                                .show()
                                        }
                                    )
                                }
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }.addOnCompleteListener {
                        mediaImage.close()
                        imageProxy.close()
                    }
            }

        }

        cameraProvider.unbindAll()
        try {
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                videoCapture,
                imageAnalysis,
                preview
            )
        } catch (exc: Exception) {
            Toast.makeText(context, exc.message, Toast.LENGTH_LONG).show()
        }

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    if (mainViewModel.shouldShowCamera) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                { previewView }, modifier = Modifier.fillMaxSize()
            )

            Surface(
                color = Color.Black.copy(alpha = 0f),
                modifier = Modifier.fillMaxSize()
            ) {
                TransparentClipLayout(
                    width = clipWidth,
                    height = clipHeight,
                    offsetY = offsetClipHeight,
                    cornerRadius = if (enableRecording) 300f else 30f
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(title = "", onGoBack = {}, type = TopAppBarType.DARK)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                start = Variables.CornerL,
                                top = 73.dp,
                                end = Variables.CornerL,
                                bottom = Variables.CornerL
                            )
                            .fillMaxWidth()
                            .fillMaxHeight()

                    ) {
                        Box(
                            modifier = Modifier
                                .width(clipWidth)
                                .height(clipHeight)
                                .border(
                                    border = BorderStroke(
                                        1.dp,
                                        Color.Transparent
                                    ),
                                    shape = RectangleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (enableRecording) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    FaceFocusLeft(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(clipWidth / 2 - 5.dp),
                                        colorFilter = ColorFilter.tint(
                                            if (headTurnLeft) {
                                                Color(0xFFB8F47A)
                                            } else
                                                Color(0xFFE7E1E5)
                                        )
                                    )

                                    FaceFocusRight(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .height(clipWidth / 2 - 5.dp),
                                        colorFilter = ColorFilter.tint(
                                            if (headTurnRight) {
                                                Color(0xFFB8F47A)
                                            } else
                                                Color(0xFFE7E1E5)
                                        )
                                    )
                                }
                                if (headTurnLeft && headTurnRight) {
                                    SuccessCheckIcon()
                                }
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TopLeftRadiusIcon()
                                        TopRightRadiusIcon()
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        BottomLeftRadiusIcon()
                                        BottomRightRadiusIcon()
                                    }
                                }
                            }
                        }

                        Text(
                            text = getGuildMessage(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFFCBC5C9),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

//    if (uri != null) {
//        val exoPlayer = remember {
//            ExoPlayer.Builder(context).build().apply {
//                setMediaItem(MediaItem.fromUri(uri!!))
//            }
//        }
//
//        Column(modifier = Modifier.fillMaxSize()) {
//            AndroidView(factory = { context ->
//                PlayerView(context).apply {
//                    player = exoPlayer
//                }
//            }, modifier = Modifier.fillMaxSize())
//
//        }
//    }

    if (mainViewModel.permissionDenied) {
        Column {
            Text("No camera")

        }
    }

}