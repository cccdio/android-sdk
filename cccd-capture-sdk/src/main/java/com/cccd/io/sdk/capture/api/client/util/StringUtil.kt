package com.cccd.io.sdk.capture.api.client.util

object StringUtil {
    private fun isNullOrEmpty(word: String?): Boolean {
        return word.isNullOrEmpty()
    }

    fun hasCharacter(word: String?): Boolean {
        return !isNullOrEmpty(word)
    }
}