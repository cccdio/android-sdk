package com.cccd.io.sdk.capture.ui.presentations.upload_face_video

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadFaceVideoSubmittedScreen(mainViewModel: MainActivityViewModel) {
    val context = LocalContext.current

    BackHandler {
        mainViewModel.navController?.popBackStack()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = "Indentity verification", onGoBack = {
            mainViewModel.navController?.popBackStack()
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (mainViewModel.videoUri != null) {
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
            }
//            VideoIcon()
//
//            Column(
//                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .padding(start = 16.dp, end = 16.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Your video has been recorded",
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Center,
//                    color = Color(0xFF1D1B1E)
//                )
//            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Button(
                onClick = {
                    val nextFlow = mainViewModel.getNextScreen()
                    if (nextFlow != null) {
                        mainViewModel.navController?.navigate(nextFlow)
                    } else {
                        mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit Video", style = MaterialTheme.typography.labelLarge)
            }
            OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Retake video", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}