package com.cccd.io.sdk.capture.ui.presentations.upload_document_photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.enums.DocumentSelection
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.cards.AcceptedDocumentCard
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.IdentificationCard
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadDocumentPhotoScreen(viewModel: MainActivityViewModel) {
    BackHandler {
        viewModel.currentFlowIndex -= 1
        viewModel.navController?.popBackStack()
    }
    Column {
        TopAppBar(title = "", onGoBack = {
            viewModel.currentFlowIndex -= 1
            viewModel.navController?.popBackStack()
        })
        Column(
            modifier = Modifier
                .padding(
                    start = Variables.CornerL,
                    top = Variables.CornerL,
                    end = Variables.CornerL,
                    bottom = Variables.CornerL
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    Variables.CornerS, Alignment.Top
                ),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    "Chọn tài liệu của bạn",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    Variables.CornerS, Alignment.Top
                ),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "TÀI LIỆU ĐƯỢC CHẤP NHẬN",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1D1B1E)
                )
                if (viewModel.getCurrentTask().config.documentSelection?.cccd == true) {
                    AcceptedDocumentCard(
                        onClick = {
                            viewModel.documentSelection = DocumentSelection.CCCD
                            viewModel.navController?.navigate(Screen.UploadDocumentPhotoCaptureScreen.route)
                        },
                        icon = { IdentificationCard() },
                        title = "Căn cước công dân (có gắn chip)"
                    )
                }

                if (viewModel.getCurrentTask().config.documentSelection?.icaoCCCD == true) {
                    AcceptedDocumentCard(
                        onClick = {
                            viewModel.documentSelection = DocumentSelection.OLD_CCCD
                            viewModel.navController?.navigate(Screen.UploadDocumentPhotoCaptureScreen.route)
                        },
                        icon = { IdentificationCard() },
                        title = "Căn cước công dân (không gắn chip)"
                    )
                }
                if (viewModel.getCurrentTask().config.documentSelection?.cmnd == true)
                    AcceptedDocumentCard(
                        onClick = {
                            viewModel.documentSelection = DocumentSelection.CMND
                            viewModel.navController?.navigate(Screen.UploadDocumentPhotoCaptureScreen.route)
                        },
                        icon = { IdentificationCard() },
                        title = "Chứng minh nhân dân"
                    )
            }

        }
    }
}