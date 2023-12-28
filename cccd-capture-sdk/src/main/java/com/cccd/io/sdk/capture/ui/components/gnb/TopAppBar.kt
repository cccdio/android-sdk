package com.cccd.io.sdk.capture.ui.components.gnb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import com.cccd.io.sdk.capture.ui.components.icons.CareLeftDarkIcon
import com.cccd.io.sdk.capture.ui.components.icons.CareLeftIcon

enum class TopAppBarType {
    LIGHT, DARK
}

@Composable
fun TopAppBar(title: String, onGoBack: () -> Unit, type: TopAppBarType = TopAppBarType.LIGHT,hiddenButton: Boolean = false) {
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 64.dp)
            .padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(!hiddenButton) {
            IconButton(onClick = { onGoBack() }) {
                if (type == TopAppBarType.LIGHT) {
                    CareLeftIcon()
                } else {
                    CareLeftDarkIcon()
                }
            }
        }


        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f).padding(start = 0.dp, top=0.dp, bottom = 0.dp, end = 40.dp),
            textAlign = TextAlign.Center
        )

    }
}
