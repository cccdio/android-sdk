package com.cccd.io.sdk.capture.ui.presentations

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
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.cards.AcceptedDocumentCard
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.CardProfileIcon
import com.cccd.io.sdk.capture.ui.components.icons.IdentificationCard
import com.cccd.io.sdk.capture.ui.components.icons.NoteBookIcon

@Composable
fun DocumentCaptureScreen(viewModel: MainActivityViewModel) {

    Column {
        TopAppBar(title = "Indentity verification", onGoBack = {
            viewModel.activity.finish()
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
                    Variables.CornerS, Alignment.Top
                ),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "ACCEPTED DOCUMENTS",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1D1B1E)
                )
                AcceptedDocumentCard(
                    onClick = { /*TODO*/ },
                    icon = { NoteBookIcon() },
                    title = "Passport"
                )
                AcceptedDocumentCard(
                    onClick = { /*TODO*/ },
                    icon = { CardProfileIcon() },
                    title = "Drive's license"
                )
                AcceptedDocumentCard(
                    onClick = { /*TODO*/ },
                    icon = { IdentificationCard() },
                    title = "National indentity card"
                )
            }
        }
    }
}