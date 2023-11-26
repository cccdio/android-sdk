package com.example.cccd_io_kotlin_android.navigations

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object VerificationScreen : Screen(route = "verification_screen")
    object TryAsGuestScreen : Screen(route = "try_as_guest_screen")
    object IntoSDKScreen : Screen(route = "intro_sdk_screen")
}
