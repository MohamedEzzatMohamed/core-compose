package com.shared.core.utils

import kotlin.math.abs
import kotlin.math.truncate


const val AMOUNT_DECIMALS = 4
private const val AMOUNT_FACTOR = 10000.0
private const val NOISE_GUARD = 1e-6

/**
 * Truncate this monetary value to 4 decimal places (toward zero). Keeps the type
 * a [Double] so request models are unchanged; emits at most 4 decimals once
 * serialized (trailing zeros are not representable in a Double).
 */
fun Double.truncateAmount(): Double {
    if (isNaN() || isInfinite()) return this
    val scaled = abs(this) * AMOUNT_FACTOR + NOISE_GUARD
    val units = truncate(scaled)
    val result = units / AMOUNT_FACTOR
    return if (this < 0) -result else result
}

/**
 * Truncate to 4 decimals and format as a fixed-width string with exactly 4
 * decimal places, e.g. 12.5 -> "12.5000", 57.477 -> "57.4770". Used for request
 * fields that are typed as `String` (JSON string or multipart/form values).
 */
fun Double.toAmount4String(): String {
    if (isNaN() || isInfinite()) return "0.0000"
    val units = truncate(abs(this) * AMOUNT_FACTOR + NOISE_GUARD).toLong()
    val whole = units / 10000
    val frac = (units % 10000).toString().padStart(AMOUNT_DECIMALS, '0')
    val sign = if (this < 0 && units != 0L) "-" else ""
    return "$sign$whole.$frac"
}
