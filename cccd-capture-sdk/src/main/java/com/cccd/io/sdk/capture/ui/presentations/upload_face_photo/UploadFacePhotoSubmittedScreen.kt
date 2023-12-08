package com.cccd.io.sdk.capture.ui.presentations.upload_face_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadFacePhotoSubmittedScreen(mainViewModel: MainActivityViewModel) {
    BackHandler(onBack = {
        mainViewModel.outputFacePhotoBitmap = null
        mainViewModel.navController?.popBackStack()
    })
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = "Verify your indentity",
            onGoBack = {
                mainViewModel.outputFacePhotoBitmap = null
                mainViewModel.navController?.popBackStack()
            })
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
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
            Text(
                text = "Make sure your entire face is visible",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            if (mainViewModel.outputFacePhotoBitmap != null) {
                Image(
                    bitmap = mainViewModel.outputFacePhotoBitmap!!.asImageBitmap(),
                    contentDescription = "image review",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topEnd = 12.dp,
                                topStart = 12.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 12.dp
                            )
                        )
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = Variables.CornerL,
                top = 24.dp,
                end = Variables.CornerL,
                bottom = 32.dp
            )
        ) {
            Button(onClick = {
                val nextFlow = mainViewModel.getNextScreen()
                if (nextFlow != null) {
                    mainViewModel.navController?.navigate(nextFlow)
                } else {
                    mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                }

            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Submit photo", style = MaterialTheme.typography.bodyLarge)
            }
            OutlinedButton(onClick = {
                mainViewModel.outputFacePhotoBitmap = null
                mainViewModel.navController?.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Retake photo", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}