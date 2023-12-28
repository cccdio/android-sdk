package com.cccd.io.sdk.capture.ui.presentations.upload_face_video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.DotOutline
import com.cccd.io.sdk.capture.ui.components.icons.WarningCircleIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadFaceVideoScreen(mainViewModel: MainActivityViewModel) {
    BackHandler {
        mainViewModel.currentFlowIndex -= 1
        mainViewModel.navController?.popBackStack()
    }

    Column {
        TopAppBar(title = "", onGoBack = {
            mainViewModel.currentFlowIndex -= 1
            mainViewModel.navController?.popBackStack()
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerL, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = Variables.CornerL, end = Variables.CornerL)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Quay video", style = MaterialTheme.typography.headlineLarge)
                Text(
                    text = "Hành động này là để xác minh bạn là người thật",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1D1B1E)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DotOutline()
                    Text(
                        text = "Đầu tiên, để khuôn mặt của bạn vào đúng khung hình",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DotOutline()
                    Text(
                        text = "Sau đó, từ từ quay đầu sang trái và phải",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(
                        start = 0.dp,
                        top = 32.dp,
                        end = 0.dp,
                        bottom = 32.dp
                    )
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(
                        color = Color(0xFFFEF7FC),
                        shape = RoundedCornerShape(size = Variables.CornerS)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            Variables.CornerL, Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            start = Variables.CornerL,
                            top = Variables.CornerS,
                            end = 24.dp,
                            bottom = Variables.CornerS
                        )
                    ) {
                        WarningCircleIcon()
                        Text(
                            text = "Khuôn mặt và nền của bạn sẽ được ghi lại trong quá trình này",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }


                }

                Button(onClick = {
                    mainViewModel.navController?.navigate(Screen.UploadFaceVideoRecorderScreen.route)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Bắt đầu ghi", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}