package com.cccd.io.sdk.capture.ui.presentations.upload_document_photo

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
import com.cccd.io.sdk.capture.configs.Config
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.enums.DocumentPhotoCaptureStep
import com.cccd.io.sdk.capture.repositories.camera.ImageResize
import com.cccd.io.sdk.capture.repositories.mrz_reader.MRZCallback
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.CircularLoading
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBarType
import com.cccd.io.sdk.capture.ui.components.gnb.TransparentClipLayout
import com.cccd.io.sdk.capture.ui.components.icons.ArrowCounterClockWiseIcon
import com.cccd.io.sdk.capture.ui.components.icons.RecordIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen
import com.cccd.io.sdk.capture.utils.Converter
import org.jmrtd.lds.icao.MRZInfo
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
fun UploadDocumentPhotoCaptureScreen(mainViewModel: MainActivityViewModel) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val configuration = LocalConfiguration.current
    var taken by remember {
        mutableStateOf(false)
    }

    val screenHeightInPx =
        Converter.convertDpToPixel(configuration.screenHeightDp.dp.value, context)
    val screenWidthInPx = Converter.convertDpToPixel(configuration.screenWidthDp.dp.value, context)
    val clipWidth = 350.dp
    val clipHeight = 200.dp
    val offsetClipHeight = 214.dp
    val clipWidthInPx = Converter.convertDpToPixel(clipWidth.value, context)
    val clipHeightInPx = Converter.convertDpToPixel(clipHeight.value, context)
    val offsetClipHeightInPx = Converter.convertDpToPixel(offsetClipHeight.value, context)

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().setTargetResolution(Size(screenWidthInPx, screenHeightInPx)).build()
    }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    fun getText(): String {
        if (mainViewModel.allowNFC)
            return "Đặt mặt sau của căn cước vào khung"

        if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT)
            return "Đặt mặt trước của căn cước vào khung"
        return "Đặt mặt sau của căn cước vào khung"
    }

    LaunchedEffect(key1 = lensFacing) {
        mainViewModel.requestCameraPermission()
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    BackHandler(onBack = {
        if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_BACK) {
            mainViewModel.photoCaptureStep = DocumentPhotoCaptureStep.DOCUMENT_FRONT
        }
        mainViewModel.navController?.popBackStack()
    })

    if (mainViewModel.shouldShowCamera) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

            Surface(
                color = Color.Black.copy(alpha = 0f), modifier = Modifier.fillMaxSize()
            ) {
                TransparentClipLayout(
                    width = clipWidth, height = clipHeight, offsetY = offsetClipHeight
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(title = "", onGoBack = {
                        if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_BACK) {
                            mainViewModel.photoCaptureStep = DocumentPhotoCaptureStep.DOCUMENT_FRONT
                        }
                        mainViewModel.navController?.popBackStack()
                    }, type = TopAppBarType.DARK)
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
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                12.dp, Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_BACK) {
                                ArrowCounterClockWiseIcon()
                            }
                            Text(
                                text = getText(),
                                style = MaterialTheme.typography.headlineMedium,
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
                            .height(132.dp)
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
                                        val output = mainViewModel.camera.imageCapture(uri = it,
                                            activity = mainViewModel.componentActivity,
                                            ImageResize(
                                                height = clipHeightInPx,
                                                width = clipWidthInPx,
                                                heightOffset = offsetClipHeightInPx,
                                                widthOffset = ((screenWidthInPx - clipWidthInPx) / 2),
                                                screenHeight = screenHeightInPx,
                                                screenWidth = screenWidthInPx,
                                            ),
                                            onCallback = {})
                                        if (mainViewModel.allowNFC) {
                                            mainViewModel.loading = true
                                            mainViewModel.mrzReader.process(
                                                output,
                                                mrzCallback = object : MRZCallback {
                                                    override fun onMRZRead(mrzInfo: MRZInfo) {
                                                        mainViewModel.mrzInfo = mrzInfo
                                                        mainViewModel.loading = false
                                                        mainViewModel.navController?.navigate(
                                                            Screen.NFCReaderScreen.route
                                                        )
                                                    }

                                                    override fun onMRZReadFailure() {
                                                        TODO("Not yet implemented")
                                                    }

                                                    override fun onFailure(e: Exception) {
                                                        TODO("Not yet implemented")
                                                    }

                                                })
                                            mainViewModel.outputDocumentBackBitmap = output
                                        } else
                                            if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
                                                mainViewModel.outputDocumentFrontBitmap = output
                                            } else {
                                                mainViewModel.outputDocumentBackBitmap = output
                                            }

                                    },
                                    onError = {
                                        CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                                            CCCDException.WorkflowUnknownResultException
                                        )
                                    })
                                if (!mainViewModel.allowNFC) {
                                    mainViewModel.captured = true
                                    mainViewModel.shouldShowCamera = false
                                }
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

    if (mainViewModel.errorMessage != "") {
        Column {
            Text(mainViewModel.errorMessage)
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

    if (mainViewModel.loading) {
        CircularLoading()
    }


    if (mainViewModel.outputDocumentFrontBitmap != null && mainViewModel.captured && mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
        mainViewModel.captured = false
        mainViewModel.navController?.navigate(
            Screen.UploadDocumentPhotoSubmittedScreen.route
        )
    }

    if (mainViewModel.outputDocumentBackBitmap != null && mainViewModel.captured && mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_BACK) {
        mainViewModel.captured = false
        mainViewModel.navController?.navigate(
            Screen.UploadDocumentPhotoSubmittedScreen.route
        )
    }
}
