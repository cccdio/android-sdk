package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowRun(
    @SerialName("id")
    val id: String
)

@Serializable
data class SdkToken(
    @SerialName("sdk_token")
    val sdkToken: String
)

@Serializable
data class GuestTrial(
    @SerialName("workflow_run")
    val workflowRun: WorkflowRun,
    @SerialName("sdk_token")
    val sdkToken: SdkToken
)