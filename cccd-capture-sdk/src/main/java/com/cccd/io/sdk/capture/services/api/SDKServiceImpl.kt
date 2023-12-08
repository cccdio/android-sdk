package com.cccd.io.sdk.capture.services.api

import com.cccd.io.sdk.capture.models.request.TaskCompletePayload
import com.cccd.io.sdk.capture.models.request.TaskStartPayload
import com.cccd.io.sdk.capture.models.response.Task
import com.cccd.io.sdk.capture.models.response.TaskStart
import com.cccd.io.sdk.capture.network.HttpRoutes
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File


suspend fun <T> handlerError(callback: suspend () -> T): T {
    try {
        return callback()
    } catch (e: RedirectResponseException) {
        throw IllegalArgumentException("Error 300: ${e.message}")
    } catch (e: ClientRequestException) {
        throw IllegalArgumentException("Error 400: ${e.message}")
    } catch (e: ServerResponseException) {
        throw IllegalArgumentException("Error 500: ${e.message}")
    } catch (e: Exception) {
        throw IllegalArgumentException("Error: ${e.message}")
    }
}

class SDKServiceImpl(private val client: HttpClient) : SDKService {
    override suspend fun getAListOfApplicationTasks(): List<Task> {
        return handlerError {
            val response = client.get {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_VERSION_ID}/tasks/applicants")
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token ${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response.body()
        }
    }

    override suspend fun startApplicationTask(
        taskId: String,
        payload: TaskStartPayload
    ): TaskStart {
        return handlerError {
            val response = client.post {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_VERSION_ID}/tasks/$taskId/start")
                contentType(ContentType.Application.Json)
                setBody(payload)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token $${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response.body()
        }

    }

    override suspend fun uploadFile(uploadUrl: String, file: File, contentType: String) {
        return handlerError {
            client.put {
                url(uploadUrl)
                setBody(file.readBytes())
                headers {
                    append(HttpHeaders.ContentType, contentType)
                }
            }
        }
    }

    override suspend fun completeApplicationTask(taskId: String, payload: TaskCompletePayload) {
        return handlerError {
            val response = client.post {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_VERSION_ID}/tasks/$taskId/complete")
                contentType(ContentType.Application.Json)
                setBody(payload)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token $${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response.body()
        }
    }

    override suspend fun clientWorkflowRunCompleteTasks() {
        TODO("Not yet implemented")
    }

}