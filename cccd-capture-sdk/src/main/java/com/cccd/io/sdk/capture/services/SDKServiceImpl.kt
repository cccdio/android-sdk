package com.cccd.io.sdk.capture.services

import io.ktor.client.HttpClient


class SDKServiceImpl(private val client: HttpClient) : SDKService {
    override suspend fun getAListOfApplicationTasks() {
        TODO()
    }

    override suspend fun startApplicationTask() {
        TODO()
    }

    override suspend fun clientWorkflowRunCompleteTasks() {
        TODO("Not yet implemented")
    }

}