package com.shared.core.utils


fun Double.formatNumber(
    precision: Int,
    thousandSeparator: String,
    decimalSeparator: String,
): String {
    val parts = formatDecimal(value = this, precision).split(".")
    val integerPart = parts[0]
    val decimalPart = if (parts.size > 1) parts[1] else ""
    val formattedInteger =
        integerPart.reversed().chunked(3).joinToString(thousandSeparator).reversed()

    return if (precision > 0) {
        "$formattedInteger$decimalSeparator$decimalPart"
    } else {
        formattedInteger
    }
}