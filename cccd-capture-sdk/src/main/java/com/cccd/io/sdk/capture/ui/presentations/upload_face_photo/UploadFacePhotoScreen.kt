package com.cccd.io.sdk.capture.ui.presentations.upload_face_photo

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
import com.cccd.io.sdk.capture.ui.components.icons.EyeGlassesIcon
import com.cccd.io.sdk.capture.ui.components.icons.UserIcon
import com.cccd.io.sdk.capture.ui.components.icons.WarningCircleIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen

@Composable
fun UploadFacePhotoScreen(mainViewModel: MainActivityViewModel) {
    BackHandler {
        mainViewModel.currentFlowIndex -= 1
        mainViewModel.navController?.popBackStack()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = "Xác thực danh tính", onGoBack = {
            mainViewModel.currentFlowIndex -= 1
            mainViewModel.navController?.popBackStack()
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = Variables.CornerL,
                    top = Variables.CornerL,
                    end = Variables.CornerL,
                    bottom = Variables.CornerL
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Chụp một bức ảnh Selfie", style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Chúng tôi sẽ so sánh điều này với tài liệu của bạn",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1D1B1E)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        Variables.CornerL,
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    UserIcon()
                    Text(
                        text = "Hãy nhìn thẳng về phía trước và đảm bảo rằng mắt của bạn được hiển thị rõ ràng",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        Variables.CornerL,
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    EyeGlassesIcon()
                    Text(
                        text = "Hãy loại bỏ bất kỳ vật gì che khuất khuôn mặt của bạn. Kính mắt là được chấp nhận",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    start = Variables.CornerL,
                    top = 32.dp,
                    end = Variables.CornerL,
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
                        text = "Khuôn mặt và phông nền của bạn sẽ được chụp trong quá trình này",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }

            Button(
                onClick = { mainViewModel.navController?.navigate(Screen.UploadFacePhotoCaptureScreen.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Chụp ảnh", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}