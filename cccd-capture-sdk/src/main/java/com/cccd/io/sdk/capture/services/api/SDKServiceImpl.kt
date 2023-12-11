package com.cccd.io.sdk.capture.services.api

import com.cccd.io.sdk.capture.models.request.TaskStartPayload
import com.cccd.io.sdk.capture.models.response.GuestTrial
import com.cccd.io.sdk.capture.models.response.Task
import com.cccd.io.sdk.capture.models.response.TaskStart
import com.cccd.io.sdk.capture.models.response.WorkflowRunComplete
import com.cccd.io.sdk.capture.network.HttpRoutes
import com.cccd.io.sdk.capture.services.token.CCCDTokenExpirationHandlerService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import java.io.File

suspend fun getErrorMessage(httpResponse: HttpResponse): com.cccd.io.sdk.capture.models.response.Error {
    return httpResponse.body()
}

suspend inline fun <reified T> handlerError(callback: () -> HttpResponse): T {
    try {
        val httpResponse = callback()

        if (httpResponse.status == HttpStatusCode.BadRequest) {
            val error = getErrorMessage(httpResponse)
            throw IllegalArgumentException(error.message)
        }

        if (httpResponse.status == HttpStatusCode.Forbidden) {
            val error = getErrorMessage(httpResponse)
            throw IllegalArgumentException(error.message)
        }

        return httpResponse.body()
    } catch (e: Exception) {
        throw IllegalArgumentException("${e.message}")
    }
}

class SDKServiceImpl(private val client: HttpClient) : SDKService {
    override suspend fun getAListOfApplicationTasks(): List<Task> {
        return handlerError {
            val response = client.get {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_RUN_ID}/tasks/applicants")
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token ${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }


            return@handlerError response
        }
    }

    override suspend fun startApplicationTask(
        taskId: String,
        payload: TaskStartPayload
    ): TaskStart {
        return handlerError {
            val response = client.post {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_RUN_ID}/tasks/$taskId/start")
                contentType(ContentType.Application.Json)
                setBody(payload)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token ${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response
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

    override suspend fun completeApplicationTask(runningTaskId: String) {
        return handlerError {
            val response = client.post {
                url("${HttpRoutes.APPLICATION_RUNNING_TASKS}/$runningTaskId/complete")
                contentType(ContentType.Application.Json)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token ${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response
        }
    }

    override suspend fun clientWorkflowRunCompleteTasks(): WorkflowRunComplete {
        return handlerError {
            val response = client.post {
                url("${HttpRoutes.APPLICATION_WORKFLOW_RUNS}/${CCCDTokenExpirationHandlerService.WORKFLOW_RUN_ID}/complete")
                contentType(ContentType.Application.Json)
                headers {
                    append("accept", "application/json")
                    append("Authorization", "Token ${CCCDTokenExpirationHandlerService.TOKEN}")
                }
            }

            return@handlerError response
        }
    }

    override suspend fun guestTrial(): GuestTrial {
        return handlerError {
            val response = client.get {
                url("${HttpRoutes.TRIAL_GUEST}")
            }

            return@handlerError response
        }
    }

}