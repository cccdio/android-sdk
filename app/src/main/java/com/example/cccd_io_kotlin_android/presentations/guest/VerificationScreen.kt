package com.example.cccd_io_kotlin_android.presentations.guest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.cards.VerificationFlowCard
import com.example.cccd_io_kotlin_android.components.gnb.TopAppBar
import com.example.cccd_io_kotlin_android.enums.VerificationFlow
import com.example.cccd_io_kotlin_android.navigations.Screen


@Composable
fun VerificationScreen(navController: NavController) {
    var flowSelected: VerificationFlow? by remember { mutableStateOf(null) }

    fun handleCardClick(flow: VerificationFlow) {
        flowSelected = flow
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "Select a verification flow", onGoBack = { navController.popBackStack() })

        Column(
            modifier = Modifier.padding(horizontal = Variables.CornerL),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "These are some of the ways you can configure CCCD.IO. Note that your users won’t be presented with this choice.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.spacedBy(Variables.CornerL, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VerificationFlowCard(
                    VerificationFlow.DOCUMENT_BIOMETRIC_MOTION,
                    flowSelected,
                    onClick = { f ->
                        handleCardClick(f)
                    })
                VerificationFlowCard(
                    VerificationFlow.DOCUMENT_BIOMETRIC_VIDEO,
                    flowSelected,
                    onClick = { f ->
                        handleCardClick(f)
                    })
                VerificationFlowCard(
                    VerificationFlow.DOCUMENT_BIOMETRIC_SELFIE,
                    flowSelected,
                    onClick = { f ->
                        handleCardClick(f)
                    })
                VerificationFlowCard(VerificationFlow.DOCUMENT_ONLY, flowSelected, onClick = { f ->
                    handleCardClick(f)
                })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Variables.CornerL,
                        top = 32.dp,
                        end = Variables.CornerL,
                        bottom = 32.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = { navController.navigate(Screen.TryAsGuestScreen.route) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = flowSelected != null
                ) {
                    Text(text = "Continue")
                }
            }
        }
    }
}