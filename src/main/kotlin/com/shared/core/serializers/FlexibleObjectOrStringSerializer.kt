package com.shared.core.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class FlexibleObjectOrStringSerializer<T>(
    private val underlyingSerializer: KSerializer<T>,
    private val fallbackFactory: (String) -> T
) : KSerializer<T?> {

    override val descriptor: SerialDescriptor = underlyingSerializer.descriptor

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): T? {
        val input = decoder as? JsonDecoder ?: return null
        return when (val element = input.decodeJsonElement()) {
            is JsonPrimitive if element.isString -> {
                fallbackFactory(element.content)
            }

            is JsonObject -> {
                input.json.decodeFromJsonElement(underlyingSerializer, element)
            }

            else -> null
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: T?) {
        if (value != null) {
            encoder.encodeSerializableValue(underlyingSerializer, value)
        } else {
            encoder.encodeNull()
        }
    }
}