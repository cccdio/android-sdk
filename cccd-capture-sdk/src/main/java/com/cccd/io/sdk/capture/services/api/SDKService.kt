package com.cccd.io.sdk.capture.services.api

import com.cccd.io.sdk.capture.enums.CCCDException
import com.cccd.io.sdk.capture.models.request.TaskCompletePayload
import com.cccd.io.sdk.capture.models.request.TaskStartPayload
import com.cccd.io.sdk.capture.models.response.Task
import com.cccd.io.sdk.capture.models.response.TaskStart
import com.cccd.io.sdk.capture.services.result.CCCDResultListenerHandlerService
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File

interface SDKService {
    suspend fun getAListOfApplicationTasks(): List<Task>
    suspend fun startApplicationTask(taskId: String, payload: TaskStartPayload): TaskStart
    suspend fun uploadFile(uploadUrl: String, file: File, contentType: String)

    suspend fun completeApplicationTask(taskId: String, payload: TaskCompletePayload)
    suspend fun clientWorkflowRunCompleteTasks()

    companion object {
        fun create(): SDKService {
            val client = HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
            }
            client.plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)
                if (originalCall.response.status.value == 403 && CCCDTokenExpirationHandlerService.tokenExpirationHandler != null) {
                    CCCDResultListenerHandlerService.resultListenerHandler?.onException(
                        CCCDException.WorkflowTokenExpiredException
                    )

                    CCCDTokenExpirationHandlerService.tokenExpirationHandler?.refreshToken { newToken ->
                        CCCDTokenExpirationHandlerService.TOKEN = newToken ?: ""
                    }
                    request.headers["Authorization"] =
                        "Token ${CCCDTokenExpirationHandlerService.TOKEN}"
                    execute(request)
                } else {
                    originalCall
                }
            }
            return SDKServiceImpl(
                client = client
            )
        }
    }
}