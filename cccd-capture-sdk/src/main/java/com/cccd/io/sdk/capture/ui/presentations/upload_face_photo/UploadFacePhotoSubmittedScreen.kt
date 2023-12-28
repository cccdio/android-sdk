package com.cccd.io.sdk.capture.ui.presentations.upload_face_photo

import android.content.ContextWrapper
import android.widget.Toast
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.enums.MediaType
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.CircularLoading
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadFacePhotoSubmittedScreen(mainViewModel: MainActivityViewModel) {
    val context = LocalContext.current

    BackHandler(onBack = {
        mainViewModel.outputFacePhotoBitmap = null
        mainViewModel.navController?.popBackStack()
    })



    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = "Xác thực danh tính",
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
                text = "Hãy đảm bảo toàn bộ khuôn mặt của bạn đã được hiển thị rõ ràng",
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
                        ),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = Variables.CornerL,
                top = 24.dp,
                end = Variables.CornerL,
                bottom = 32.dp
            )
        ) {
            Button(onClick = {
                mainViewModel.outputFacePhotoBitmap?.let {
                    mainViewModel.uploadPhoto(
                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                            ContextWrapper(
                                context
                            )
                        ),
                        bitmap = it,
                        mediaType = MediaType.FACE_PHOTO.value
                    ) {
                        val nextFlow = mainViewModel.getNextScreen()
                        if (nextFlow != null) {
                            mainViewModel.navController?.navigate(nextFlow)
                        } else {
                            mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                        }
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Gửi ảnh", style = MaterialTheme.typography.bodyLarge)
            }
            OutlinedButton(onClick = {
                mainViewModel.outputFacePhotoBitmap = null
                mainViewModel.navController?.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Chụp lại ảnh", style = MaterialTheme.typography.labelLarge)
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