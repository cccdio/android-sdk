package com.cccd.io.sdk.capture.network

object HttpRoutes {
    private const val BASE_URL = ""
    const val APPLICATION_TASKS = "$BASE_URL/tasks"
    const val WORKFLOW_RUNS = "$BASE_URL/workflow_runs"
    const val TRIAL_GUEST = "https://api.cccd.io/v1/guest-trial/sample-client-api"
}