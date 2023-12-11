package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskStart(
    @SerialName("file_name")
    val fileName: String,
    @SerialName("running_task_id")
    val runningTaskId: String,
    @SerialName("upload_url")
    val uploadUrl: String,
    @SerialName("workflow_version_id")
    val workflowVersionId: String
)
