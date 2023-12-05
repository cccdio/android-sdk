package com.cccd.io.sdk.capture.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.presentations.BiometricsMotionScreen
import com.cccd.io.sdk.capture.ui.presentations.DocumentCaptureScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsMotionScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsVideoCameraErrorScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsVideoCameraSubmitScreen
import com.cccd.io.sdk.capture.ui.presentations.ScanDocumentScreen
import com.cccd.io.sdk.capture.ui.presentations.ScanDocumentSubmittedScreen
import com.cccd.io.sdk.capture.ui.presentations.VerificationCompleteScreen

@Composable
fun Navigation(mainViewModel: MainActivityViewModel) {
    val navController = rememberNavController()
    mainViewModel.navController = navController
    NavHost(
        navController = navController,
        startDestination = Screen.BiometricsMotionScreen.route
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
        composable(Screen.BiometricsMotionScreen.route) {
            BiometricsMotionScreen(mainViewModel = mainViewModel)
        }
    }
}