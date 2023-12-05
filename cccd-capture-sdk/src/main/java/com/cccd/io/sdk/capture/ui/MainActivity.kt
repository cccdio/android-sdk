package com.cccd.io.sdk.capture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.cccd.io.sdk.capture.repositories.connectivity.ConnectivityObserver
import com.cccd.io.sdk.capture.repositories.connectivity.NetworkConnectivityObserver
import com.cccd.io.sdk.capture.ui.navigations.Navigation
import com.example.cccd_io_kotlin_android.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainActivityViewModel = MainActivityViewModel(
        componentActivity = this,
        activity = this@MainActivity
    )
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val status by connectivityObserver.observe()
                        .collectAsState(initial = ConnectivityObserver.Status.Available)

                    if (status != ConnectivityObserver.Status.Available) {
                        AlertDialog(
                            onDismissRequest = {}, confirmButton = {},
                            title = {
                                Text(
                                    text = "Dữ liệu di động của bạn đã bị tắt",
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center
                                )
                            },
                            text = {
                                Text(
                                    text = "Bật dữ liệu di động hoặc sử dụng Wi-fi để truy cập dữ liệu",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    } else {
                        Navigation(mainViewModel)
                    }
                }
            }
        }
    }

}
