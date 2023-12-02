package com.cccd.io.sdk.capture.api.client

import com.cccd.io.sdk.capture.api.client.serializers.LocaleAsStringSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

private val jsonParser = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
    explicitNulls = false
    serializersModule = SerializersModule {
        contextual(LocaleAsStringSerializer)
    }
    coerceInputValues = true
}

fun getJsonParserInstance(): Json =
    jsonParser