package com.cccd.io.sdk.capture.navigations

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cccd.io.sdk.capture.presentations.DocumentCaptureScreen

@Composable
fun Navigation(activity: Activity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DocumentCaptureScreen.route) {
        composable(Screen.DocumentCaptureScreen.route) {
            DocumentCaptureScreen(navController)
        }
    }
}