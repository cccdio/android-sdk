package com.cccd.io.sdk.capture.ui.presentations.upload_document_photo

import android.content.ContextWrapper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.enums.DocumentPhotoCaptureStep
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.CircularLoading
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.RedWarningCircleIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadDocumentPhotoSubmittedScreen(mainViewModel: MainActivityViewModel) {
    val context = LocalContext.current
    BackHandler(onBack = {
        mainViewModel.errorMessage = ""
        if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
            mainViewModel.outputDocumentFrontBitmap = null
        } else {
            mainViewModel.outputDocumentBackBitmap = null
        }
        mainViewModel.navController?.popBackStack()
    })



    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = "Xác thực danh tính",
            onGoBack = {
                mainViewModel.errorMessage = ""
                if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
                    mainViewModel.outputDocumentFrontBitmap = null
                } else {
                    mainViewModel.outputDocumentBackBitmap = null
                }
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
            if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
                if (mainViewModel.errorMessage != "") {
                    Row(
                        modifier = Modifier

                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(1.dp, color = Color(0xFFBA1A1A)),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 24.dp,
                                    top = 24.dp,
                                    end = 24.dp,
                                    bottom = 24.dp
                                )
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                Variables.CornerL,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RedWarningCircleIcon()
                            Text(
                                text = mainViewModel.errorMessage,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }
                }
                if (mainViewModel.outputDocumentFrontBitmap != null) {
                    Image(
                        bitmap = mainViewModel.outputDocumentFrontBitmap!!.asImageBitmap(),
                        contentDescription = "image review",
                        modifier = Modifier
                            .width(350.dp)
                            .height(200.dp)
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
            } else {
                if (mainViewModel.outputDocumentBackBitmap != null) {
                    Image(
                        bitmap = mainViewModel.outputDocumentBackBitmap!!.asImageBitmap(),
                        contentDescription = "image review",
                        modifier = Modifier
                            .width(350.dp)
                            .height(200.dp)
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 12.dp,
                                    topStart = 12.dp,
                                    bottomEnd = 12.dp,
                                    bottomStart = 12.dp
                                )
                            ),
                    )
                }
            }
        }
        Text(
            text = "Hãy đảm bảo thông tin của bạn rõ ràng và không bị che khuất",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
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
            Button(enabled = mainViewModel.errorMessage == "", onClick = {
                (if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) mainViewModel.outputDocumentFrontBitmap else mainViewModel.outputDocumentBackBitmap)?.let {
                    mainViewModel.uploadPhoto(
                        outputDirectory = mainViewModel.camera.getOutputDirectory(
                            ContextWrapper(
                                context
                            )
                        ),
                        bitmap = it,
                        mediaType = mainViewModel.getDocumentMediaType()
                    ) {
                        if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT) {
                            mainViewModel.photoCaptureStep = DocumentPhotoCaptureStep.DOCUMENT_BACK
                            mainViewModel.navController?.navigate(Screen.UploadDocumentPhotoCaptureScreen.route)
                            mainViewModel.shouldShowCamera = true
                        } else {
                            val nextFlow = mainViewModel.getNextScreen()
                            if (nextFlow != null) {
                                mainViewModel.navController?.navigate(nextFlow)
                            } else {
                                mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
                            }
                        }
                    }
                }

            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Gửi ảnh", style = MaterialTheme.typography.bodyLarge)
            }
            OutlinedButton(onClick = {
                mainViewModel.errorMessage = ""
                if (mainViewModel.photoCaptureStep == DocumentPhotoCaptureStep.DOCUMENT_FRONT)
                    mainViewModel.outputDocumentFrontBitmap = null
                else
                    mainViewModel.outputDocumentBackBitmap = null
                mainViewModel.navController?.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Chụp lại ảnh", style = MaterialTheme.typography.labelLarge)
            }
        }
    }

    if (mainViewModel.loading) {
        CircularLoading()
    }
}