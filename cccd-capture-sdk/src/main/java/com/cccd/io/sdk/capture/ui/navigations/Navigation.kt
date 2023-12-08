package com.cccd.io.sdk.capture.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cccd.io.sdk.capture.ui.MainActivityViewModel
import com.cccd.io.sdk.capture.ui.presentations.FlashScreen
import com.cccd.io.sdk.capture.ui.presentations.RecordBiometricsVideoCameraErrorScreen
import com.cccd.io.sdk.capture.ui.presentations.VerificationCompleteScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_document_photo.UploadDocumentPhotoCaptureScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_document_photo.UploadDocumentPhotoScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_document_photo.UploadDocumentPhotoSubmittedScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_motion.UploadFaceMotionRecorderScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_motion.UploadFaceMotionScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_photo.UploadFacePhotoCaptureScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_photo.UploadFacePhotoScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_photo.UploadFacePhotoSubmittedScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_video.UploadFaceVideoRecorderScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_video.UploadFaceVideoScreen
import com.cccd.io.sdk.capture.ui.presentations.upload_face_video.UploadFaceVideoSubmittedScreen

@Composable
fun Navigation(mainViewModel: MainActivityViewModel) {
    val navController = rememberNavController()
    mainViewModel.navController = navController
    NavHost(
        navController = navController,
        startDestination = Screen.FlashScreen.route
    ) {
        composable(Screen.UploadDocumentPhotoScreen.route) {
            UploadDocumentPhotoScreen(mainViewModel)
        }
        composable(Screen.UploadDocumentPhotoCaptureScreen.route) {
            UploadDocumentPhotoCaptureScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadDocumentPhotoSubmittedScreen.route) {
            UploadDocumentPhotoSubmittedScreen(mainViewModel = mainViewModel)
        }

        composable(Screen.UploadFaceMotionScreen.route) {
            UploadFaceMotionScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadFaceMotionRecorderScreen.route) {
            UploadFaceMotionRecorderScreen(mainViewModel = mainViewModel)
        }


        composable(Screen.UploadFaceVideoScreen.route) {
            UploadFaceVideoScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadFaceVideoRecorderScreen.route) {
            UploadFaceVideoRecorderScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadFaceVideoSubmittedScreen.route) {
            UploadFaceVideoSubmittedScreen(mainViewModel = mainViewModel)
        }

        composable(Screen.UploadFacePhotoScreen.route) {
            UploadFacePhotoScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadFacePhotoCaptureScreen.route) {
            UploadFacePhotoCaptureScreen(mainViewModel = mainViewModel)
        }
        composable(Screen.UploadFacePhotoSubmittedScreen.route) {
            UploadFacePhotoSubmittedScreen(mainViewModel = mainViewModel)
        }


        composable(Screen.RecordBiometricsVideoCameraErrorScreen.route) {
            RecordBiometricsVideoCameraErrorScreen()
        }

        composable(Screen.VerificationCompleteScreen.route) {
            VerificationCompleteScreen()
        }

        composable(Screen.FlashScreen.route) {
            FlashScreen(mainViewModel = mainViewModel)
        }
    }
}