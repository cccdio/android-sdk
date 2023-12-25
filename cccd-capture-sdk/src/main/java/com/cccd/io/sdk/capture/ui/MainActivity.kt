package com.cccd.io.sdk.capture.ui

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.enums.NFCReaderStatus
import com.cccd.io.sdk.capture.models.data.Eid
import com.cccd.io.sdk.capture.repositories.connectivity.ConnectivityObserver
import com.cccd.io.sdk.capture.repositories.connectivity.NetworkConnectivityObserver
import com.cccd.io.sdk.capture.repositories.nfc_reader.EidCallback
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.ui.navigations.Navigation
import com.example.cccd_io_kotlin_android.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    private lateinit var connectivityObserver: ConnectivityObserver
    val mainViewModel = MainActivityViewModel(
        componentActivity = this,
        activity = this@MainActivity
    )
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mainViewModel.shouldShowCamera = true
        } else {
            CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                CCCDException.WorkflowCameraPermissionException
            )
            mainViewModel.permissionDenied = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        mainViewModel.requestPermissionLauncher = requestPermissionLauncher

        mainViewModel.nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        mainViewModel.pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_MUTABLE
            )

        setContent {
            AppTheme {
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

    public override fun onResume() {
        super.onResume()
        mainViewModel.nfcAdapter?.enableForegroundDispatch(
            this,
            mainViewModel.pendingIntent,
            null,
            null
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mainViewModel.mrzInfo?.let {
            mainViewModel.nfcReader.read(
                this@MainActivity,
                intent,
                it,
                eidCallback = object : EidCallback {
                    override fun onEidReadStart() {
                        mainViewModel.nfcReaderStatus = NFCReaderStatus.START
                    }

                    override fun onEidReadFinish() {
                        mainViewModel.nfcReaderStatus = NFCReaderStatus.FINISHED
                    }

                    override fun onEidRead(passport: Eid) {
                        mainViewModel.eid = passport
                    }

                    override fun onEidReadError(e: Exception) {
                        mainViewModel.nfcReaderStatus = NFCReaderStatus.ERROR
                    }

                })
        }
    }
}
