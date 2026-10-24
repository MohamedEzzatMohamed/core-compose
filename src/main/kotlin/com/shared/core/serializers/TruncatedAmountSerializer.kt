package com.shared.core.serializers

import com.shared.core.utils.truncateAmount
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull

object TruncatedAmountSerializer : KSerializer<Double> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("TruncatedAmount", PrimitiveKind.DOUBLE)

    override fun deserialize(decoder: Decoder): Double {
        val jsonDecoder = decoder as? JsonDecoder ?: return decoder.decodeDouble()
        val primitive = jsonDecoder.decodeJsonElement() as? JsonPrimitive ?: return 0.0
        return primitive.doubleOrNull ?: primitive.content.toDoubleOrNull() ?: 0.0
    }

    override fun serialize(encoder: Encoder, value: Double) {
        encoder.encodeDouble(value.truncateAmount())
    }
}