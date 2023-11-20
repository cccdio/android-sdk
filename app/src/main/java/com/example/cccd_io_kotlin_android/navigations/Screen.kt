package com.example.cccd_io_kotlin_android.navigations

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object VerificationScreen : Screen(route = "verification_screen")
}
