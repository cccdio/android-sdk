package com.cccd.io.sdk.capture.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.icons.NoteBookIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcceptedDocumentCard(onClick: () -> Unit, icon: @Composable () -> Unit, title: String) {
    OutlinedCard(
        onClick = { onClick() },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    Variables.CornerS, Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(32.dp)
                    .padding(
                        start = Variables.CornerS,
                        top = 6.dp,
                        end = Variables.CornerL,
                        bottom = 6.dp
                    )
            ) {
                icon()
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF4A454E),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun P() {
    AcceptedDocumentCard(onClick = {}, icon = {
        NoteBookIcon()
    }, "dasd")
}