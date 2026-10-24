package com.shared.core.extensions


fun String.formatDecimalAmount(length: Int = 2): String {
    val dotIndex = indexOf('.')
    if (dotIndex == -1) return this
    val maxAllowedLength = dotIndex + 1 + length
    return if (this.length > maxAllowedLength) {
        substring(0, maxAllowedLength)
    } else {
        this
    }
}