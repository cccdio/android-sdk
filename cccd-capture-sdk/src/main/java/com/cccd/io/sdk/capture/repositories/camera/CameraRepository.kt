package com.cccd.io.sdk.capture.repositories.camera

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
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

    fun recordVideo(
        filenameFormat: String,
        videoCapture: VideoCapture<Recorder>,
        context: Context,
        outputDirectory: File,
        onVideoRecord: (Uri) -> Unit,
        onError: (error: String) -> Unit
    ): Recording

    fun imageCapture(
        uri: Uri,
        activity: ComponentActivity,
        imageResize: ImageResize,
        onCallback: () -> Unit
    ): Bitmap

    fun getOutputDirectory(context: ContextWrapper): File

}