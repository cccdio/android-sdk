package com.cccd.io.sdk.capture.ui.presentations.upload_face_video

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.cccd.io.sdk.capture.configs.Config
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.enums.MediaType
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.CircularLoading
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.navigations.Screen
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun UploadFaceVideoSubmittedScreen(
    mainViewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val file = mainViewModel.videoUri?.path?.let { File(it) }

    var showErrorMessage by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showErrorMessage) {
        delay(1000)
        showErrorMessage = true
    }

    BackHandler {
        mainViewModel.navController?.popBackStack()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = "Xác thực danh tính", onGoBack = {
            mainViewModel.navController?.popBackStack()
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            if (mainViewModel.videoUri != null && file != null && file.exists()) {
                val exoPlayer = remember {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(mainViewModel.videoUri!!))
                    }
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    AndroidView(factory = { context ->
                        PlayerView(context).apply {
                            player = exoPlayer
                        }
                    }, modifier = Modifier.fillMaxSize())

                }
            } else if (showErrorMessage) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Video không tồn tại")
                    Text(text = "Vui lòng thực hiện lại bước xác minh này")
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Button(
                onClick = {
                    val fileName = "CameraX-recording-" +
                            SimpleDateFormat(
                                Config.FILE_NAME_FORMAT,
                                Locale.US
                            )
                                .format(System.currentTimeMillis()) + ".mp4"
                    if (file != null) {
                        mainViewModel.uploadVideo(
                            fileName,
                            file = file,
                            mediaType = MediaType.FACE_VIDEO.value
                        ) {
                            val nextFlow = mainViewModel.getNextScreen()
                            if (nextFlow != null) {
                                mainViewModel.navController?.navigate(nextFlow)
                            } else {
                                mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                            }
                            file.delete()
                            showErrorMessage = false
                        }
                    } else {
                        CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                            CCCDException.WorkflowUnknownResultException
                        )
                        val nextFlow = mainViewModel.getNextScreen()
                        if (nextFlow != null) {
                            mainViewModel.navController?.navigate(nextFlow)
                        } else {
                            mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                enabled = (file != null) && file.exists()
            ) {
                Text(text = "Gửi video", style = MaterialTheme.typography.labelLarge)
            }
            OutlinedButton(
                onClick = { mainViewModel.navController?.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Bắt đầu lại", style = MaterialTheme.typography.labelLarge)
            }
        }
    }

    if (mainViewModel.loading) {
        CircularLoading()
    }

    if (mainViewModel.errorMessage != "") {
        Toast.makeText(context, mainViewModel.errorMessage, Toast.LENGTH_LONG).show()
        mainViewModel.errorMessage = ""
    }
}