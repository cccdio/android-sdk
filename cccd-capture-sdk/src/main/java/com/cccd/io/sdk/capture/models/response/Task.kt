package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val id: String,
    @SerialName("task_def_id")
    val taskDefId: String,
    val config: UploaderConfig,
    @SerialName("workflow_version_id")
    val workflowVersionId: String,
    @SerialName("minimum_sdk_versions")
    val minimumSdkVersions: MinimumSdkVersions,
)