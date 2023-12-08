package com.cccd.io.sdk.capture.enums

enum class CCCDException {
    WorkflowInsufficientVersionException,

    //    WorkflowInvalidSSLCertificateException,
    WorkflowTokenExpiredException,
    WorkflowUnknownCameraException,
    WorkflowCameraPermissionException,

    //    WorkflowUnknownResultException,
//    WorkflowUnsupportedTaskException,
    WorkflowHttpException,
//    WorkflowUnknownException
}