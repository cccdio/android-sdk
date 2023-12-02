package com.cccd.io.sdk.capture.ui.options

import java.io.Serializable

class FlowStep(action: FlowAction) : Serializable {
    private val action: FlowAction = action
    val WELCOME: FlowStep = FlowStep(FlowAction.WELCOME)
    val CAPTURE_DOCUMENT: FlowStep = FlowStep(FlowAction.CAPTURE_DOCUMENT)
    val CAPTURE_FACE: FlowStep = FlowStep(FlowAction.CAPTURE_FACE)
    val FINAL: FlowStep = FlowStep(FlowAction.FINAL)
    val PROOF_OF_ADDRESS: FlowStep = FlowStep(FlowAction.PROOF_OF_ADDRESS)
    val DYNAMIC_CONTENT: FlowStep = FlowStep(FlowAction.DYNAMIC_CONTENT)
}