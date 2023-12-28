package com.cccd.io.sdk.capture.ui.presentations.upload_face_video

import android.content.Context
import android.content.ContextWrapper
import android.media.Image
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.cccd.io.sdk.capture.configs.Config
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.repositories.face_detection.FaceMeshBoundingBox
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBarType
import com.cccd.io.sdk.capture.ui.components.gnb.TransparentOvalLayout
import com.cccd.io.sdk.capture.ui.components.icons.ArrowLeftIcon
import com.cccd.io.sdk.capture.ui.components.icons.ArrowRightIcon
import com.cccd.io.sdk.capture.ui.components.icons.SuccessCheckIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen
import com.cccd.io.sdk.capture.utils.Converter
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
fun UploadFaceVideoRecorderScreen(
    mainViewModel: MainActivityViewModel
) {
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var recording: Recording? by remember {
        mutableStateOf(null)
    }

    val configuration = LocalConfiguration.current


    var enableTurn by remember {
        mutableStateOf(false)
    }

    var enableSuccess by remember {
        mutableStateOf(false)
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
    var timerTurn by remember { mutableIntStateOf(0) }

    val screenHeightInPx =
        Converter.convertDpToPixel(configuration.screenHeightDp.dp.value, context)
    val screenWidthInPx = Converter.convertDpToPixel(configuration.screenWidthDp.dp.value, context)
    val clipWidth = 273.dp
    val clipHeight = 389.dp
    val offsetClipHeight = 80.dp
    val clipWidthInPx = Converter.convertDpToPixel(clipWidth.value, context)
    val clipHeightInPx = Converter.convertDpToPixel(clipHeight.value, context)
    val offsetClipHeightInPx = Converter.convertDpToPixel(offsetClipHeight.value, context)


    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }

    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    val recorder = Recorder.Builder().build()
    val videoCapture = remember {
        VideoCapture.withOutput(recorder)
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
                timer = 0
            }
            return@LaunchedEffect
        }

        if (timer == 0) {
            if (recording != null) {
                recording?.stop()
                recording = null
            }
        }
    }

    LaunchedEffect(timerTurn) {
        if (timerTurn > 0) {
            delay(1000)
            timerTurn -= 1
            enableTurn = true
        }
    }

    LaunchedEffect(key1 = enableSuccess) {
        if (enableSuccess) {
            delay(1000)
            enableSuccess = false
        }
    }


    LaunchedEffect(key1 = lensFacing) {
        mainViewModel.requestCameraPermission()
        val cameraProvider = context.getCameraProvider()

        val imageAnalysis = ImageAnalysis.Builder().build()
        imageAnalysis.setAnalyzer(
            mainViewModel.cameraExecutor
        ) { imageProxy ->
            val mediaImage: Image? = imageProxy.image

            mediaImage?.let { it ->
                val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
                val detector =
                    FaceDetection.getClient(mainViewModel.faceDetection.getFaceDetectorOptions())

                detector.process(image).addOnSuccessListener { faces ->
                    for (face in faces) {
                        val bounds = face.boundingBox

                        if (enableRecording && enableTurn) {
                            if (!headTurnLeft) {
                                headTurnLeft =
                                    face.headEulerAngleY > Config.HEAD_ROTATION_AMPLITUDE
                                enableSuccess =
                                    face.headEulerAngleY > Config.HEAD_ROTATION_AMPLITUDE

                            }

                            if (headTurnLeft && !headTurnRight) {
                                headTurnRight =
                                    face.headEulerAngleY < -Config.HEAD_ROTATION_AMPLITUDE
                                if (!enableSuccess) {
                                    enableSuccess = headTurnRight
                                }
                            }

                            if (headTurnLeft && headTurnRight && !enableSuccess) {
                                enableSuccess = true
                            }
                        }


                        val startPointPercent = bounds.left.toFloat() / image.width
                        val topPointPercent = bounds.top.toFloat() / image.height


                        val faceWidthPercent = bounds.width().toFloat() / image.width
                        val faceHeightPercent = bounds.height().toFloat() / image.height

                        val faceMeshBoundingBox = FaceMeshBoundingBox(
                            width = screenWidthInPx * faceWidthPercent * Config.SCALE_X,
                            height = screenWidthInPx * faceHeightPercent * Config.SCALE_Y * image.height / image.width,
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

                        if (!enableRecording && hasFaceInBox(faceMeshBoundingBox) && hasOpenEyes) {
                            enableRecording = true
                            enableSuccess = true
                            timer = Config.TIME_RECORD
                            timerTurn = 1
                            if (recording == null) {
                                recording =
                                    mainViewModel.camera.recordVideo(filenameFormat = Config.FILE_NAME_FORMAT,
                                        videoCapture = videoCapture,
                                        context = context,
                                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                                            ContextWrapper(
                                                context
                                            )
                                        ),
                                        onVideoRecord = { url, file ->
                                            mainViewModel.videoUri = url
                                            if (!headTurnLeft || !headTurnRight) {
                                                file.delete()
                                                headTurnLeft = false
                                                headTurnRight = false
                                                enableTurn = false
                                                enableRecording = false
                                            } else {
                                                mainViewModel.navController?.navigate(Screen.UploadFaceVideoSubmittedScreen.route)
                                            }
                                        },
                                        onError = {
                                            Toast.makeText(context, it, Toast.LENGTH_LONG)
                                                .show()
                                            CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                                                CCCDException.WorkflowUnknownResultException
                                            )
                                        })
                            }
                        }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                        CCCDException.WorkflowUnknownCameraException
                    )
                }.addOnCompleteListener {
                    mediaImage.close()
                    imageProxy.close()
                }
            }

        }

        cameraProvider.unbindAll()
        try {
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, videoCapture, imageAnalysis, preview
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
                color = Color.Black.copy(alpha = 0f), modifier = Modifier.fillMaxSize()
            ) {
                TransparentOvalLayout(
                    width = clipWidth,
                    height = clipHeight,
                    offsetY = offsetClipHeight,
                    color = if (enableSuccess) Color(
                        0xFF4CAF50
                    ) else Color(
                        0xFFCBC5C9
                    ),
                    dashed = !enableRecording,
                    success = headTurnLeft && headTurnRight,
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(title = "", onGoBack = {
                        mainViewModel.navController?.popBackStack()
                    }, type = TopAppBarType.DARK)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                start = Variables.CornerL,
                                top = Variables.CornerL,
                                end = Variables.CornerL,
                                bottom = Variables.CornerL
                            )
                            .fillMaxWidth()
                            .fillMaxHeight()

                    ) {
                        Box(
                            modifier = Modifier
                                .width(clipWidth)
                                .height(clipHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            if (enableRecording && !enableTurn) {
                                SuccessCheckIcon()
                            }
                            if (headTurnLeft && headTurnRight) {
                                SuccessCheckIcon()
                            }
                        }
                        if (enableRecording && enableTurn) {
                            if (headTurnLeft) {
                                if (!headTurnRight && !enableSuccess) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(
                                            Variables.CornerS, Alignment.Top
                                        ),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            text = "Quay đầu sang phải",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = Color(0xFFCBC5C9),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                        ArrowRightIcon()
                                        Text(
                                            text = "Sau đó quay về phía trước",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = Color(0xFFCBC5C9),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        Variables.CornerS, Alignment.Top
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = "Quay đầu sang trái",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0xFFCBC5C9),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    ArrowLeftIcon()
                                    Text(
                                        text = "Sau đó quay về phía trước",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color(0xFFCBC5C9),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "Đưa khuôn mặt của bạn vào trong khung hình để bắt đầu quay",
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
    }

    if (mainViewModel.permissionDenied) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Camera không được cấp quyền")
        }
    }
}