package com.example.cccd_io_kotlin_android.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cccd.io.sdk.capture.ui.components.Variables
import com.example.cccd_io_kotlin_android.R
import com.example.cccd_io_kotlin_android.components.icons.PlusIcon
import com.example.cccd_io_kotlin_android.enums.VerificationFlow

@Composable
fun VerificationFlowCardIcon(flow: VerificationFlow) {
    when (flow) {
        VerificationFlow.DOCUMENT_BIOMETRIC_MOTION ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    Variables.CornerL,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.id_card),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )

                PlusIcon()

                Image(
                    painter = painterResource(id = R.drawable.fingerprint),
                    contentDescription = "fingerprint description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }

        VerificationFlow.DOCUMENT_BIOMETRIC_VIDEO, VerificationFlow.DOCUMENT_BIOMETRIC_SELFIE -> Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                Variables.CornerL,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.id_card),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )

            PlusIcon()

            Image(
                painter = painterResource(id = R.drawable.face_recognition),
                contentDescription = "face recognition description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }


        VerificationFlow.DOCUMENT_ONLY -> Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                Variables.CornerL,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.id_card),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationFlowCard(
    flow: VerificationFlow,
    selected: VerificationFlow?,
    onClick: (flow: VerificationFlow) -> Unit
) {

    val content =
        when (flow) {
            VerificationFlow.DOCUMENT_BIOMETRIC_MOTION -> "Tài liệu & Chuyển động sinh trắc học"
            VerificationFlow.DOCUMENT_BIOMETRIC_VIDEO -> "Tài liệu & Video sinh trắc học"
            VerificationFlow.DOCUMENT_BIOMETRIC_SELFIE -> "Tài liệu & Ảnh tự chụp sinh trắc học"
            VerificationFlow.DOCUMENT_ONLY -> "Chỉ tài liệu"
        }

    val isSelected = selected == flow



    OutlinedCard(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF7B41B3) else Color(0xFFCDC4CE),
                shape = RoundedCornerShape(size = 12.dp)
            )
            ,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFEF7FC) else Color.White
        ),
        onClick = { onClick(flow) }
    ) {
        Column(
            modifier = Modifier.padding(start = 40.dp, top = 14.dp, end = 40.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerificationFlowCardIcon(flow)

            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

