package com.cccd.io.sdk.capture.ui

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.cccd.io.sdk.capture.ui.repositories.CameraRepositoryImpl
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivityViewModel(
    val navController: NavHostController,
    val componentActivity: ComponentActivity,
    val activity: Activity
) : ViewModel() {
    val cameraExecutor: Executor = Executors.newSingleThreadExecutor()
    val camera = CameraRepositoryImpl()

    var outputBitmap: Bitmap? by mutableStateOf(null)
    var shouldShowCamera: Boolean by mutableStateOf(false)
    var permissionDenied: Boolean by mutableStateOf(false)
    var captured: Boolean by mutableStateOf(false)

    fun convertDpToPixel(dp: Float): Int {
        return ((dp * (componentActivity.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt())
    }

    fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                componentActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                shouldShowCamera = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                componentActivity,
                android.Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> componentActivity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    shouldShowCamera = true
                } else {
                    permissionDenied = true
                }
            }
        }
    }

}