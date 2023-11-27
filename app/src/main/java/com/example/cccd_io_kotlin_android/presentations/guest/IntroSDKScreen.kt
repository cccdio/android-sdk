package com.example.cccd_io_kotlin_android.presentations.guest

import android.app.Activity
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
import com.cccd.io.sdk.capture.factories.CCCDFactory
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.gnb.TopAppBar
import com.example.cccd_io_kotlin_android.components.images.IllustrationImage


@Composable
fun IntroSDKScreen(navController: NavController, activity: Activity) {
    val context = LocalContext.current

    fun startVerification() {
        val client = CCCDFactory.create(context).client

        val cccdConfig = CCCDConfig.builder(context)
            .withSDKToken("")
            .build()

        client.startActivityForResult(activity, 1, cccdConfig)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(title = "Indentify verification", onGoBack = {
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
                            text = "We will perform a Document and Selfie Photo check.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1D1B1E)
                        )
                        Text(
                            text = "The flow is the experience your users will get when you integrate with CCCD.IO",
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
                    text = "CCCD.IO Android SDK 20.5.2",
                    style = MaterialTheme.typography.bodySmall
                )
                Button(modifier = Modifier.fillMaxWidth(), onClick = { startVerification() }) {
                    Text(
                        text = "Start verification",
                    )
                }
            }
        }
    }
}
