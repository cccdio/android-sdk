package com.cccd.io.sdk.capture.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Output(
    @SerialName("val")
    val `val`: String
)

@Serializable
data class WorkflowRunComplete(
    @SerialName("applicant_id")
    val applicantId: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("current_tasks")
    val currentTasks: List<String> = listOf(),
    @SerialName("error")
    val error: String,
    @SerialName("id")
    val id: String,
    @SerialName("next_tasks")
    val nextTasks: List<String> = listOf(),
    @SerialName("output")
    val output: Output,
    @SerialName("reasons")
    val reasons: List<String> = listOf(),
    @SerialName("status")
    val status: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("workflow_id")
    val workflowId: String,
    @SerialName("workflow_version_id")
    val workflowVersionId: String
)
