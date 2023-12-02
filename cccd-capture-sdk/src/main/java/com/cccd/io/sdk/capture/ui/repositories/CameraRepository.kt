package com.cccd.io.sdk.capture.ui.repositories

import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.util.concurrent.Executor

interface CameraRepository {
    fun takePhoto(
        filenameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    )

    fun imageCapture(
        uri: Uri,
        activity: ComponentActivity,
        imageResize: ImageResize,
        onCallback: () -> Unit
    ): Bitmap

    fun getOutputDirectory(context: ContextWrapper): File

}