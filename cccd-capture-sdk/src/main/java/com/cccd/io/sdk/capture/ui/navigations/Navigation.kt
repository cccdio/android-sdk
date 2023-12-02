package com.cccd.io.sdk.capture.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.presentations.DocumentCaptureScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsMotionScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsVideoCameraErrorScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsVideoCameraSubmitScreen
import com.cccd.io.sdk.capture.ui.presentations.ScanDocumentScreen
import com.cccd.io.sdk.capture.ui.presentations.ScanDocumentSubmittedScreen
import com.cccd.io.sdk.capture.ui.presentations.VerificationCompleteScreen

@Composable
fun Navigation(mainViewModel: MainActivityViewModel) {
    NavHost(
        navController = mainViewModel.navController,
        startDestination = Screen.ScanDocumentScreen.route
    ) {
        composable(Screen.DocumentCaptureScreen.route) {
            DocumentCaptureScreen(mainViewModel)
        }

        composable(Screen.RecordBiometricsMotionScreen.route) {
            RecordBiometricsMotionScreen()
        }

        composable(Screen.RecordBiometricsVideoCameraErrorScreen.route) {
            RecordBiometricsVideoCameraErrorScreen()
        }

        composable(Screen.RecordBiometricsVideoCameraSubmitScreen.route) {
            RecordBiometricsVideoCameraSubmitScreen()
        }

        composable(Screen.VerificationCompleteScreen.route) {
            VerificationCompleteScreen()
        }
        composable(Screen.ScanDocumentScreen.route) {
            ScanDocumentScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.ScanDocumentSubmittedScreen.route) {
            ScanDocumentSubmittedScreen(mainViewModel = mainViewModel)
        }
    }
}