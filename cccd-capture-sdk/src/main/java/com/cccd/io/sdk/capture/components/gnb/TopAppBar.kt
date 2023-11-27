package com.cccd.io.sdk.capture.components.gnb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.components.icons.CareLeftIcon

@Composable
fun TopAppBar(title: String, onGoBack: () -> Unit) {
    Row(
        modifier = Modifier
            .height(64.dp)
            .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { onGoBack() }) {
            CareLeftIcon()
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

    }
}
