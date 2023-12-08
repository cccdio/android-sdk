package com.cccd.io.sdk.capture.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskStartPayload(
    @SerialName("file_name")
    val fileName: String,
    @SerialName("workflow_version_id")
    val workflowVersionId: String
)

@Serializable
data class TaskCompletePayload(
    @SerialName("workflow_version_id")
    val workflowVersionId: String
)