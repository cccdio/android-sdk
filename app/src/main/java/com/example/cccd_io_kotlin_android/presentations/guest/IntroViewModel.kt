package com.example.cccd_io_kotlin_android.presentations.guest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cccd.io.sdk.capture.services.api.SDKService
import kotlinx.coroutines.launch

class IntroViewModel : ViewModel() {
    private val client = SDKService.create()

    var token by mutableStateOf("")
    var workflowRunId by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var loading by mutableStateOf(false)

    fun callApi(callback: () -> Unit) {
        viewModelScope.launch {
            try {
                loading = true
                val guest = client.guestTrial()
                token = guest.sdkToken.sdkToken
                workflowRunId = guest.workflowRun.id
                callback()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            } finally {
                loading = false
            }
        }
    }

}