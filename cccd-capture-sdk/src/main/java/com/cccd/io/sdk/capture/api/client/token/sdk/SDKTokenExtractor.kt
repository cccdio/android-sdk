package com.cccd.io.sdk.capture.api.client.token.sdk

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object SDKTokenExtractor {
    private const val HEADER_INDEX = 0
    private const val PAYLOAD_INDEX = 1
    private const val SIGNATURE_INDEX = 2
    private const val TOKEN_TOTAL_PART_COUNT = 3
    private const val SDK_TOKEN_GROUP_SEPARATOR = "\\."
    fun decodeHeader(token: String?): String? {
        return getTokenPart(token, HEADER_INDEX)
    }

    fun decodePayload(token: String?): String? {
        return getTokenPart(token, PAYLOAD_INDEX)
    }

    fun decodeSignature(token: String?): String? {
        return getTokenPart(token, SIGNATURE_INDEX)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getTokenPart(token: String?, tokenPartIndex: Int): String? {
        val tokenParts = token?.split(SDK_TOKEN_GROUP_SEPARATOR.toRegex())
            ?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        return if (tokenParts != null && tokenParts.size == TOKEN_TOTAL_PART_COUNT) Base64.decode(
            tokenParts[tokenPartIndex].toString()
        ).toString() else null
    }
}
