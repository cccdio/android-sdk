package com.example.cccd_io_kotlin_android.presentations.guest

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cccd.io.sdk.capture.CCCDConfig
import com.cccd.io.sdk.capture.CCCDFactory
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandler
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandler
import com.cccd.io.sdk.capture.ui.components.CircularLoading
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.gnb.TopAppBar
import com.example.cccd_io_kotlin_android.components.images.IllustrationImage


class ExpirationHandler : CCCDTokenExpirationHandler {
    override fun refreshToken(injectNewToken: (String?) -> Unit) {
        val introViewModel = IntroViewModel()

        introViewModel.callApi {
            injectNewToken(introViewModel.token)
        }
    }
}

class ResultListenerHandler : CCCDResultListenerHandler {
    override fun userCompleted() {}

    override fun onException(exception: CCCDException) {
        when (exception) {
            CCCDException.WorkflowInsufficientVersionException -> {}
            CCCDException.WorkflowCameraPermissionException -> {}
            CCCDException.WorkflowHttpException -> {}
            CCCDException.WorkflowTokenExpiredException -> {}
            CCCDException.WorkflowUnknownResultException -> {}
            else -> {}
        }
    }
}

@Composable
fun IntroSDKScreen(navController: NavController, activity: Activity) {
    val introViewModel = IntroViewModel()
    val context = LocalContext.current

    fun startVerification() {
        introViewModel.callApi {
            val client = CCCDFactory.create().client
            val token = introViewModel.token
            val workflowRunId = introViewModel.workflowRunId

            val cccdConfig = CCCDConfig.builder()
                .withSDKToken(token, tokenExpirationHandler = ExpirationHandler())
                .withWorkflowRunId(workflowRunId)
                .build()

            client.startActivityForResult(activity, cccdConfig)
            client.handleActivityResult(result = ResultListenerHandler())
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(title = "Xác thực danh tính", onGoBack = {
            navController.popBackStack()
        })
        Row(
            modifier = Modifier
                .weight(1F)
                .padding(start = Variables.CornerL, end = Variables.CornerL),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(59.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IllustrationImage()

                Column(
                    verticalArrangement = Arrangement.spacedBy(Variables.CornerM, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(text = "The CCCD.IO SDK", style = MaterialTheme.typography.headlineLarge)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            Variables.CornerM,
                            Alignment.Top
                        ),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = "Chúng tôi sẽ thực hiện kiểm tra tài liệu và ảnh Selfie.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1D1B1E)
                        )
                        Text(
                            text = "Đây là trải nghiệm mà người dùng của bạn sẽ nhận được khi tích hợp với CCCD.IO.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1D1B1E)
                        )
                    }
                }
            }

        }
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Text(
                    text = "CCCD.IO Android SDK 1.0.0",
                    style = MaterialTheme.typography.bodySmall
                )
                Button(modifier = Modifier.fillMaxWidth(), onClick = { startVerification() }) {
                    Text(
                        text = "Bắt đầu xác minh",
                    )
                }
            }
        }
    }

    if (introViewModel.loading) {
        CircularLoading()
    }

    if (introViewModel.errorMessage != "") {
        Toast.makeText(context, introViewModel.errorMessage, Toast.LENGTH_LONG).show()
        introViewModel.errorMessage = ""
    }
}
