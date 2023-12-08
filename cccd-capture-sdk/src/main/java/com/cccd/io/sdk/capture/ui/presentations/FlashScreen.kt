package com.cccd.io.sdk.capture.ui.presentations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.cccd.io.sdk.capture.ui.MainActivityViewModel

@Composable
fun FlashScreen(
    mainViewModel: MainActivityViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (mainViewModel.firstFlagScreen) {
                    mainViewModel.activity.finish()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (mainViewModel.loading) {
            CircularProgressIndicator()
        } else {
            if (mainViewModel.errorMessage == "") {
                if (!mainViewModel.firstFlagScreen) {
                    mainViewModel.firstFlagScreen = true
                    val startScreen = mainViewModel.getNextScreen()
                    if (startScreen != null) {
                        mainViewModel.navController?.navigate(startScreen)
                    }
                }
            } else {
                Text(text = mainViewModel.errorMessage)
            }
        }
    }
}
