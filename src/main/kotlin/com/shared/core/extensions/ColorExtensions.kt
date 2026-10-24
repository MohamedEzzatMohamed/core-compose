package com.shared.core.extensions

import androidx.compose.ui.graphics.Color


fun String.parseStationColor(hex: String): Color {
    if (hex.isBlank()) return Color(0xFF4CAF50)
    return try {
        val cleaned = hex.removePrefix("#")
        Color(("FF$cleaned").toLong(16))
    } catch (_: Exception) {
        Color(0xFF4CAF50)
    }
}
