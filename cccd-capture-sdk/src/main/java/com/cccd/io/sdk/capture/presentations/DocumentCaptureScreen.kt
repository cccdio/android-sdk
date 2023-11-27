package com.cccd.io.sdk.capture.presentations

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
import androidx.navigation.NavController
import com.cccd.io.sdk.capture.components.Variables
import com.cccd.io.sdk.capture.components.gnb.TopAppBar

@Composable
fun DocumentCaptureScreen(navController: NavController) {

    Column {
        TopAppBar(title = "Indentity verification", onGoBack = {})
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
                    Variables.CornerS,
                    Alignment.Top
                ),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    "Choose your document",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Select issuing country to see which documents we accept",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    Variables.CornerS,
                    Alignment.Top
                ),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "ACCEPTED DOCUMENTS",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1D1B1E)
                )
            }
        }
    }
}