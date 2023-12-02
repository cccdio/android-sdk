package com.cccd.io.sdk.capture.ui.presentations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar

@Composable
fun ScanDocumentSubmittedScreen(mainViewModel: MainActivityViewModel) {
    BackHandler(onBack = {
        mainViewModel.outputBitmap = null
        mainViewModel.navController.popBackStack()
    })
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = "Verify your indentity",
            onGoBack = { mainViewModel.navController.popBackStack() })
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
            if (mainViewModel.outputBitmap != null) {
                Image(
                    bitmap = mainViewModel.outputBitmap!!.asImageBitmap(),
                    contentDescription = "image review",
                    modifier = Modifier
                        .width(350.dp)
                        .height(200.dp)
                )
            }
        }
        Text(
            text = "Make sure your details are clear and unobstructed",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
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
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Submit photo", style = MaterialTheme.typography.bodyLarge)
            }
            OutlinedButton(onClick = {
                mainViewModel.outputBitmap = null
                mainViewModel.navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Retake photo", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}