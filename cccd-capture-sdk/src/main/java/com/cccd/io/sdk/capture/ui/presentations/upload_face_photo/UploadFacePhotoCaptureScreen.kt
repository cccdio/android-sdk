package com.cccd.io.sdk.capture.ui.presentations.upload_face_photo

import android.content.Context
import android.content.ContextWrapper
import android.media.Image
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.cccd.io.sdk.capture.configs.Config
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.repositories.face_detection.FaceMeshBoundingBox
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBarType
import com.cccd.io.sdk.capture.ui.components.gnb.TransparentClipLayout
import com.cccd.io.sdk.capture.ui.components.icons.RecordIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen
import com.cccd.io.sdk.capture.utils.Converter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
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
fun UploadFacePhotoCaptureScreen(mainViewModel: MainActivityViewModel) {
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val configuration = LocalConfiguration.current

    val screenHeightInPx =
        Converter.convertDpToPixel(configuration.screenHeightDp.dp.value, context)
    val screenWidthInPx = Converter.convertDpToPixel(configuration.screenWidthDp.dp.value, context)
    val clipWidth = 273.dp
    val clipHeight = 389.dp
    val clipCorner = 387.17926.dp
    val offsetClipHeight = 80.dp
    val clipWidthInPx = Converter.convertDpToPixel(clipWidth.value, context)
    val clipHeightInPx = Converter.convertDpToPixel(clipHeight.value, context)
    val offsetClipHeightInPx = Converter.convertDpToPixel(offsetClipHeight.value, context)

    var enableCapture by remember {
        mutableStateOf(false)
    }

    var taken by remember {
        mutableStateOf(false)
    }


    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

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
                        for (face in faces) {
                            val bounds = face.boundingBox


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

                            enableCapture = hasFaceInBox(faceMeshBoundingBox) && hasOpenEyes

                        }
                    }
                    .addOnFailureListener { e ->
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
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, imageCapture, imageAnalysis
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    if (mainViewModel.shouldShowCamera) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

            Surface(
                color = Color.Black.copy(alpha = 0f), modifier = Modifier.fillMaxSize()
            ) {
                TransparentClipLayout(
                    width = clipWidth,
                    height = clipHeight,
                    offsetY = offsetClipHeight,
                    cornerRadius = clipCorner.value
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(title = "", onGoBack = {
                        mainViewModel.navController?.popBackStack()
                    }, type = TopAppBarType.DARK)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                start = Variables.CornerL,
                                top = Variables.CornerL,
                                end = Variables.CornerL,
                                bottom = Variables.CornerL
                            )
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            Modifier
                                .border(
                                    width = 2.184.dp,
                                    color = Color(0xFFCBC5C9),
                                    shape = RoundedCornerShape(size = clipCorner)
                                )
                                .width(clipWidth)
                                .height(clipHeight)
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                12.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Text(
                                text = "Hãy giữ khuôn mặt của bạn nằm trong hình oval",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFCBC5C9),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

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
                                0.dp, Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(start = 4.dp, top = 32.dp, end = 4.dp, bottom = 32.dp)
                        ) {
                            if (enableCapture) {
                                IconButton(onClick = {
                                    taken = true
                                    mainViewModel.camera.takePhoto(filenameFormat = Config.FILE_NAME_FORMAT,
                                        imageCapture = imageCapture,
                                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                                            ContextWrapper(
                                                context
                                            )
                                        ),
                                        executor = mainViewModel.cameraExecutor,
                                        onImageCaptured = {
                                            val output =
                                                mainViewModel.camera.imageCapture(
                                                    uri = it,
                                                    activity = mainViewModel.componentActivity,
                                                    imageResize = null,
                                                    onCallback = {}
                                                )
                                            mainViewModel.outputFacePhotoBitmap = output
                                        },
                                        onError = {
                                            CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                                                CCCDException.WorkflowUnknownResultException
                                            )
                                        })
                                    mainViewModel.captured = true
                                    mainViewModel.shouldShowCamera = false
                                }, content = {
                                    RecordIcon()
                                }, modifier = Modifier
                                    .width(64.dp)
                                    .height(64.dp), enabled = !taken
                                )
                            }
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

    if (mainViewModel.outputFacePhotoBitmap != null && mainViewModel.captured) {
        mainViewModel.captured = false
        mainViewModel.navController?.navigate(
            Screen.UploadFacePhotoSubmittedScreen.route
        )
    }

}