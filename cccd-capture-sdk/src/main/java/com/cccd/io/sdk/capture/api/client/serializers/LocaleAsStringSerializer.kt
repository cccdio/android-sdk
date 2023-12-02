package com.cccd.io.sdk.capture.api.client.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Locale

object LocaleAsStringSerializer : KSerializer<Locale> {

    override val descriptor: SerialDescriptor =
        LocaleProps.serializer().descriptor

    override fun deserialize(decoder: Decoder): Locale {
        val value: LocaleProps = decoder.decodeSerializableValue(LocaleProps.serializer())

        return Locale(value.language, value.country)
    }

    override fun serialize(encoder: Encoder, value: Locale) {
        encoder.encodeSerializableValue(
            LocaleProps.serializer(),
            LocaleProps(value.language, value.country)
        )
    }
}

@Serializable
private data class LocaleProps(
    @SerialName("language")
    val language: String,
    @SerialName("country")
    val country: String,
)
