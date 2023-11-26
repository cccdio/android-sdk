package com.example.cccd_io_kotlin_android.navigations

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cccd_io_kotlin_android.presentations.guest.VerificationScreen
import com.example.cccd_io_kotlin_android.presentations.main.HomeScreen

@Composable
fun Navigation(activity: Activity) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Screen.VerificationScreen.route) {
            VerificationScreen(navController)
        }
    }
}