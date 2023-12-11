package com.cccd.io.sdk.capture.repositories.camera

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor


class CameraRepositoryImpl : CameraRepository {
    override fun takePhoto(
        filenameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()


        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                        CCCDException.WorkflowUnknownCameraException
                    )
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    onImageCaptured(savedUri)
                    photoFile.delete()
                }
            })
    }

    override fun recordVideo(
        filenameFormat: String,
        videoCapture: VideoCapture<Recorder>,
        context: Context,
        outputDirectory: File,
        onVideoRecord: (uri: Uri, outputFile: File) -> Unit,
        onError: (error: String) -> Unit
    ): Recording {
        val name = "CameraX-recording-" +
                SimpleDateFormat(filenameFormat, Locale.US)
                    .format(System.currentTimeMillis()) + ".mp4"

        val outputFile = File(
            outputDirectory,
            name
        )

        val outputOptions = FileOutputOptions.Builder(outputFile).build()


        return videoCapture.output
            .prepareRecording(context, outputOptions)
            .start(ContextCompat.getMainExecutor(context)) { event ->
                when (event) {
                    is VideoRecordEvent.Finalize -> {
                        if (event.hasError()) {
                            CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                                CCCDException.WorkflowUnknownCameraException
                            )
                            onError("Quá trình quay video không thành công!")
                        } else {
                            onVideoRecord(event.outputResults.outputUri, outputFile)
                        }
                    }
                }
            }
    }


    override fun imageCapture(
        uri: Uri,
        activity: ComponentActivity,
        imageResize: ImageResize?,
        onCallback: () -> Unit
    ): Bitmap {
        val bitmapOrigin = BitmapFactory.decodeStream(
            activity.contentResolver.openInputStream(uri)
        )



        if (imageResize != null) {
            val rotationMatrix = Matrix()
            rotationMatrix.postRotate(90f)

            val bitmap =
                Bitmap.createBitmap(
                    bitmapOrigin,
                    0,
                    0,
                    bitmapOrigin.width,
                    bitmapOrigin.height,
                    rotationMatrix,
                    true
                )

            val cropWidth =
                imageResize.width * bitmap.width / imageResize.screenWidth
            val cropHeight =
                imageResize.height * bitmap.height / imageResize.screenHeight
            val widthOffset =
                imageResize.widthOffset * bitmap.width / imageResize.screenWidth
            val heightOffset =
                imageResize.heightOffset * bitmap.height / imageResize.screenHeight
            onCallback()

            return Bitmap.createBitmap(
                bitmap,
                widthOffset,
                heightOffset - 10,
                cropWidth,
                cropHeight + 10
            )
        }

        val rotationMatrix = Matrix()
        rotationMatrix.postRotate(-90f)


        return Bitmap.createBitmap(
            bitmapOrigin,
            0,
            0,
            bitmapOrigin.width,
            bitmapOrigin.height,
            rotationMatrix,
            true
        )

    }

    override fun getOutputDirectory(context: ContextWrapper): File {
        val mediaDir = context.externalMediaDirs.firstOrNull().let {
            File(it, "Android").apply { mkdirs() }
        }

        return if (mediaDir.exists()) mediaDir else context.filesDir
    }

}