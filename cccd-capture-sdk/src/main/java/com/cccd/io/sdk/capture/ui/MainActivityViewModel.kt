package com.cccd.io.sdk.capture.ui

import android.app.Activity
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.nfc.NfcAdapter
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.cccd.io.sdk.capture.configs.Config
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.enums.DocumentPhotoCaptureStep
import com.cccd.io.sdk.capture.enums.DocumentSelection
import com.cccd.io.sdk.capture.enums.FlowStep
import com.cccd.io.sdk.capture.enums.MediaType
import com.cccd.io.sdk.capture.enums.NFCReaderStatus
import com.cccd.io.sdk.capture.models.data.Eid
import com.cccd.io.sdk.capture.models.request.TaskStartPayload
import com.cccd.io.sdk.capture.models.response.Task
import com.cccd.io.sdk.capture.models.response.TaskStart
import com.cccd.io.sdk.capture.repositories.camera.CameraRepository
import com.cccd.io.sdk.capture.repositories.face_detection.FaceDetectionRepository
import com.cccd.io.sdk.capture.repositories.mrz_reader.MRZReaderRepository
import com.cccd.io.sdk.capture.repositories.nfc_reader.NFCReader
import com.cccd.io.sdk.capture.services.api.SDKService
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.navigations.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jmrtd.lds.icao.MRZInfo
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivityViewModel(
    var navController: NavHostController? = null,
    val componentActivity: ComponentActivity,
    val activity: Activity
) : ViewModel() {
    private val client = SDKService.create()
    val cameraExecutor: Executor = Executors.newSingleThreadExecutor()
    val camera = CameraRepository.create()
    val faceDetection = FaceDetectionRepository.create()
    val mrzReader = MRZReaderRepository.create()
    val nfcReader = NFCReader.create()

    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null
    var nfcReaderStatus: NFCReaderStatus by mutableStateOf(NFCReaderStatus.START)
    var eid: Eid? by mutableStateOf(null)

    var loading: Boolean by mutableStateOf(true)
    var firstFlagScreen: Boolean by mutableStateOf(false)
    var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    var allowNFC: Boolean by mutableStateOf(false)
    var mrzInfo: MRZInfo? by mutableStateOf(null)

    var errorMessage by mutableStateOf("")

    private var flowSteps = ArrayList<String>()
    private var taskList = ArrayList<Task>()
    var currentFlowIndex = -1
    var documentSelection: DocumentSelection? = null

    // Upload document photo
    var photoCaptureStep: DocumentPhotoCaptureStep by mutableStateOf(DocumentPhotoCaptureStep.DOCUMENT_FRONT)

    var outputDocumentFrontBitmap: Bitmap? by mutableStateOf(null)
    var outputDocumentBackBitmap: Bitmap? by mutableStateOf(null)

    var outputFacePhotoBitmap: Bitmap? by mutableStateOf(null)

    var videoUri: Uri? by mutableStateOf(null)

    var shouldShowCamera: Boolean by mutableStateOf(false)
    var permissionDenied: Boolean by mutableStateOf(false)
    var captured: Boolean by mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                val allTasks = client.getAListOfApplicationTasks()
                val hashFlow = HashMap<String, Boolean>()
                hashFlow[FlowStep.UPLOAD_DOCUMENT_PHOTO.value] = true
                hashFlow[FlowStep.UPLOAD_FACE_VIDEO.value] = true
                hashFlow[FlowStep.UPLOAD_FACE_PHOTO.value] = true
                hashFlow[FlowStep.UPLOAD_FACE_MOTION.value] = true

                val hashRoute = HashMap<String, String>()
                hashRoute[FlowStep.UPLOAD_DOCUMENT_PHOTO.value] =
                    Screen.UploadDocumentPhotoScreen.route
                hashRoute[FlowStep.UPLOAD_FACE_MOTION.value] = Screen.UploadFaceMotionScreen.route
                hashRoute[FlowStep.UPLOAD_FACE_VIDEO.value] = Screen.UploadFaceVideoScreen.route
                hashRoute[FlowStep.UPLOAD_FACE_PHOTO.value] = Screen.UploadFacePhotoScreen.route

                for (task in allTasks) {
                    if (hashFlow[task.taskDefId] == true) {
                        taskList.add(task)
                        hashRoute[task.taskDefId]?.let {
                            flowSteps.add(it)
                        }
                    }
                }
            } catch (e: Exception) {
                CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                    CCCDException.WorkflowHttpException
                )
                errorMessage = e.message.toString()
            } finally {
                loading = false
            }
        }
    }

    fun uploadVideo(fileName: String, mediaType: String, file: File, onCallback: () -> Unit) {
        viewModelScope.launch {
            try {
                loading = true
                val currentTask = taskList[currentFlowIndex]

                val taskStart: TaskStart = client.startApplicationTask(
                    currentTask.id, TaskStartPayload(
                        fileName = fileName,
                        mediaType = mediaType
                    )
                )

                client.uploadFile(
                    taskStart.uploadUrl,
                    file,
                    "video/mp4"
                )

                client.completeApplicationTask(
                    runningTaskId = taskStart.runningTaskId
                )

                onCallback()
            } catch (e: Exception) {
                CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                    CCCDException.WorkflowHttpException
                )
                errorMessage = e.message.toString()
            } finally {
                loading = false
            }
        }
    }

    fun uploadPhoto(
        outputDirectory: File,
        mediaType: String,
        bitmap: Bitmap,
        onCallback: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                loading = true
                val currentTask = taskList[currentFlowIndex]
                val fileName = SimpleDateFormat(Config.FILE_NAME_FORMAT, Locale.US).format(
                    System.currentTimeMillis()
                ) + ".jpg"
                val taskStart: TaskStart = client.startApplicationTask(
                    currentTask.id, TaskStartPayload(
                        fileName = fileName,
                        mediaType = mediaType
                    )
                )
                val file = File(outputDirectory, fileName)

                withContext(Dispatchers.IO) {
                    val stream: OutputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.flush()
                    stream.close()
                }

                client.uploadFile(
                    taskStart.uploadUrl,
                    file,
                    "image/jpeg"
                )

                file.delete()

                client.completeApplicationTask(
                    runningTaskId = taskStart.runningTaskId
                )

                onCallback()
            } catch (e: Exception) {
                CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                    CCCDException.WorkflowHttpException
                )
                errorMessage = e.message.toString()
            } finally {
                loading = false
            }
        }
    }

    fun getDocumentMediaType(): String {
        if (photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
            return when (documentSelection) {
                DocumentSelection.CCCD -> MediaType.CCCD_DOCUMENT_PHOTO_FRONT.value
                DocumentSelection.OLD_CCCD -> MediaType.OLD_CCCD_DOCUMENT_PHOTO_FRONT.value
                DocumentSelection.CMND -> MediaType.CMND_DOCUMENT_PHOTO_FRONT.value
                else -> ""
            }
        }

        return when (documentSelection) {
            DocumentSelection.CCCD -> MediaType.CCCD_DOCUMENT_PHOTO_BACK.value
            DocumentSelection.OLD_CCCD -> MediaType.OLD_CCCD_DOCUMENT_PHOTO_BACK.value
            DocumentSelection.CMND -> MediaType.CMND_DOCUMENT_PHOTO_BACK.value
            else -> ""
        }
    }

    fun handleWorkflowComplete() {
        viewModelScope.launch {
            try {
                loading = true
                client.clientWorkflowRunCompleteTasks()
            } catch (e: Exception) {
                CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                    CCCDException.WorkflowHttpException
                )
                errorMessage = e.message.toString()
            } finally {
                loading = false
            }
        }
    }

    fun getNextScreen(): String? {
        if (currentFlowIndex < flowSteps.size - 1 && flowSteps.isNotEmpty()) {
            currentFlowIndex += 1
            return flowSteps[currentFlowIndex]
        }

        return null
    }

    fun getCurrentTask(): Task {
        return taskList[currentFlowIndex]
    }

    fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                componentActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                shouldShowCamera = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                componentActivity,
                android.Manifest.permission.CAMERA
            ) -> {
                CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                    CCCDException.WorkflowCameraPermissionException
                )
                requestPermissionLauncher?.launch(android.Manifest.permission.CAMERA)
            }

            else -> requestPermissionLauncher?.launch(android.Manifest.permission.CAMERA)
        }
    }

}