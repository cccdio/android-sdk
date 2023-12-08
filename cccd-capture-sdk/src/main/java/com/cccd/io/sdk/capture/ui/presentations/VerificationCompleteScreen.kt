package com.cccd.io.sdk.capture.ui.presentations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.IllustrationIcon

@Composable
fun VerificationCompleteScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = "Indentity verification", onGoBack = {})
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            IllustrationIcon()

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Verification complete", style = MaterialTheme.typography.headlineLarge)
                Text(
                    text = "This is what your users will experience when they get verified.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1D1B1E)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    CCCDResultListenerHandlerService.resultListenerHandler?.userCompleted()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Text(text = "Get in touch", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}