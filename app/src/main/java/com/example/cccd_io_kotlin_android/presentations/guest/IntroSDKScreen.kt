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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cccd.io.sdk.capture.CCCDConfig
import com.cccd.io.sdk.capture.CCCDFactory
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandler
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandler
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.gnb.TopAppBar
import com.example.cccd_io_kotlin_android.components.images.IllustrationImage


class ExpirationHandler : CCCDTokenExpirationHandler {
    override fun refreshToken(injectNewToken: (String?) -> Unit) {
        injectNewToken("UGp9_a0GIru8wk_-z-VJk7JpLdw4MKlNXowgEsuHMlNhxuFPoi3QKml7lWobhr7rPQljfFgv1fFI9bxI60uys5M69KebGhifJvtr7wXWrua9DMRFKh262uwepHOmbHLso8__1JAS7JuA_zPOZ5ol4BB3kdACMsf5Ky2RBEw9mLlGJvP_Qto_vk3IryfiTm91250d9Xe2GOkGcJ6HALPmR7teN9PAfIcBN5G5yHaaTMQiOxjiUPfShhuyxVzJ1_lHaiV-OkWZgqqeS8ccQiQ7nRE2Ba7dSBL1WWEj926pSmTuvxpMLdInC2RwSXvJ2HKJbkaAqY54VhsoD3_P5yCC9OHaverFEqb92M88bMzpN9n9uOabwB5Cvkx2CKE2TBP_0BCJO3jTNoarxKXOO7nvy2nE_sHaJ5NdyEF65VAhxaef6u0iKL_r3Ty4z7sEt4cHem31zLHPrin6KuI7QtBEmu7bD4KXnzBIuvJCy8Zcm6M")
    }
}

class ResultListenerHandler : CCCDResultListenerHandler {
    override fun userCompleted() {
        TODO()
    }

    override fun onException(exception: CCCDException) {
        when (exception) {
            CCCDException.WorkflowInsufficientVersionException -> {}
            CCCDException.WorkflowCameraPermissionException -> {}
            CCCDException.WorkflowHttpException -> {}
            CCCDException.WorkflowTokenExpiredException -> {}
            else -> {}
        }
    }

}

@Composable
fun IntroSDKScreen(navController: NavController, activity: Activity) {
    fun startVerification() {
        val client = CCCDFactory.create().client
        val token =
            "RvhmG0eHUkyMnhH6gFSHakm9polMu3SqukAUw7UuaN2DUD-U7Bice5DYkqyWEELLrTe4RsqIZUvwhaDezux_Y2B3JcHDwk5Y5tQrK-sH-2p4190xmNpYm_wxpiSUELdnbdc1Dbl-2ikQc7fPk1C5rt6waT4gDJwDb9I4upQbQXcX0qN66OFsx3TptssErdkblBGg4yNaibzV4H2n5bdhho7Fx6wK1LuOIeQT_IDZLo9teSpWBXW_ArcZgeOph-i2WobGW8VH1ZhPK_H4Xgost5JyVPJE6M5zaHf_IfXLG1v2jrHpVpyjp7R5fjks-i5UA-k2gbQPe07zy9NSL7f92XAebaQ5LhmEI3EqLdZ6JU77ZSBhGnyc_mZJT2yBeB2j6cSaC8W-p9tvUPoGM5WIVRtVRz2bPKEWfJ6aDYR4o8WdILYPXrMQvBuSVnNIeLkEjj4inmROzzLIxB2iEd5ODc9vOmt5MIKFOudJbjcECr4"
        val workflowVersionId = "c781f204-a5c6-4708-9187-9fae9c3c0d8c"
        val cccdConfig = CCCDConfig.builder()
            .withSDKToken(token, workflowVersionId, tokenExpirationHandler = ExpirationHandler())
            .build()

        client.startActivityForResult(activity, cccdConfig)
        client.handleActivityResult(result = ResultListenerHandler())
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
                    text = "CCCD.IO Android SDK 1.0.0",
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
