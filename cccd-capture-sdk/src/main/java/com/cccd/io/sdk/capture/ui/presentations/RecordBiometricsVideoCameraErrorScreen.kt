package com.cccd.io.sdk.capture.ui.presentations

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
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.icons.EyeIcon
import com.cccd.io.sdk.capture.ui.components.icons.FaceMaskIcon
import com.cccd.io.sdk.capture.ui.components.icons.SmileyIcon
import com.cccd.io.sdk.capture.ui.components.icons.SunDimIcon
import com.cccd.io.sdk.capture.ui.components.icons.XCircleIcon

@Composable
fun RecordBiometricsVideoCameraErrorScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 64.dp)
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                XCircleIcon()
                Text(
                    text = "We can’t detect your face",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        start = 16.dp,
                        top = Variables.CornerS,
                        end = 24.dp,
                        bottom = Variables.CornerS
                    )
                ) {
                    SunDimIcon()
                    Text(
                        text = "Make sure to be in a place with good lighting",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B1E)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        start = 16.dp,
                        top = Variables.CornerS,
                        end = 24.dp,
                        bottom = Variables.CornerS
                    )
                ) {
                    EyeIcon()
                    Text(
                        text = "Make sure your eyes are clearly visible",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B1E)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        start = 16.dp,
                        top = Variables.CornerS,
                        end = 24.dp,
                        bottom = Variables.CornerS
                    )
                ) {
                    FaceMaskIcon()
                    Text(
                        text = "Make sure to remove masks or other items that cover your face. Eyeglasses are okay",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B1E)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterHorizontally
                    ), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                        start = 16.dp,
                        top = Variables.CornerS,
                        end = 24.dp,
                        bottom = Variables.CornerS
                    )
                ) {
                    SmileyIcon()
                    Text(
                        text = "Make sure to only show your face, we don’t need to see your ID",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1D1B1E)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Button(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Restart recording", style = MaterialTheme.typography.labelLarge)
            }
        }

    }
}
