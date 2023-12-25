package com.cccd.io.sdk.capture.ui.presentations.upload_document_photo

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.cccd.io.sdk.capture.enums.NFCReaderStatus
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.components.Variables
import com.cccd.io.sdk.capture.ui.components.gnb.BackHandler
import com.cccd.io.sdk.capture.ui.components.gnb.TopAppBar
import com.cccd.io.sdk.capture.ui.components.icons.NFCIcon
import com.cccd.io.sdk.capture.ui.navigations.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NFCReaderScreen(
    mainViewModel: MainActivityViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                if (!NfcAdapter.getDefaultAdapter(context).isEnabled) {
                    Toast.makeText(context, "Cần kích hoạt NFC", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(context, intent, null)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    BackHandler {
        mainViewModel.mrzInfo = null
        mainViewModel.navController?.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(title = "", onGoBack = {
            mainViewModel.mrzInfo = null
            mainViewModel.navController?.popBackStack()
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = Variables.CornerL,
                top = 120.dp,
                end = Variables.CornerL,
                bottom = Variables.CornerL
            )
        ) {
            NFCIcon()
            LinearProgressIndicator()
            Text(
                text = "Đặt thiết bị lên mặt sau của căn cước và không di chuyển",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }



    if (mainViewModel.nfcReaderStatus === NFCReaderStatus.FINISHED) {
        Toast.makeText(context, "Đọc dữ liệu thành công", Toast.LENGTH_LONG).show()
        mainViewModel.nfcReaderStatus = NFCReaderStatus.START
        val nextFlow = mainViewModel.getNextScreen()
        if (nextFlow != null) {
            mainViewModel.navController?.navigate(nextFlow)
        } else {
            mainViewModel.navController?.navigate(Screen.VerificationCompleteScreen.route)
        }
    }
}