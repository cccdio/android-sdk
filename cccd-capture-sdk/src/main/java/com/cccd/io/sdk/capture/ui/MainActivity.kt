package com.cccd.io.sdk.capture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cccd.io.sdk.capture.ui.navigations.Navigation
import com.example.cccd_io_kotlin_android.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    mainViewModel =
                        MainActivityViewModel(
                            navController,
                            componentActivity = this,
                            activity = this@MainActivity
                        )
                    Navigation(mainViewModel)
                }
            }
        }
    }

}
