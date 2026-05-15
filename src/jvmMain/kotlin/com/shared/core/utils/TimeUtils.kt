@file:JvmName("TimeUtilsDesktop")

package com.shared.core.utils

import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun formatDateTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    return formatter.format(Date(timestamp))
}

actual fun formatDecimal(value: Double, precision: Int): String {
    return String.format(Locale.US, "%.${precision}f", value)
}

actual fun formatDecimalWithCommas(value: Double, precision: Int): String {
    return String.format(Locale.US, "%,.${precision}f", value)
}

actual fun formatPaddedInt(value: Int, width: Int): String {
    return String.format(Locale.US, "%0${width}d", value)
}

actual fun parseDateTimeToMillis(dateTimeStr: String): Long? {
    // Server timestamps are in UTC. Normalize ISO 8601 format
    // (e.g. "2026-02-01T15:04:28.000000Z") to "yyyy-MM-dd HH:mm:ss"
    val normalized = dateTimeStr
        .replace("T", " ")
        .removeSuffix("Z")
        .replace(Regex("\\.\\d+$"), "") // strip fractional seconds
    val utc = TimeZone.getTimeZone("UTC")
    return try {
        val formatter =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).apply { timeZone = utc }
        formatter.parse(normalized)?.time
    } catch (e: Exception) {
        try {
            val altFormatter =
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).apply { timeZone = utc }
            altFormatter.parse(normalized)?.time
        } catch (e2: Exception) {
            null
        }
    }
}

actual fun formatTime12Hour(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}

actual fun formatDate(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}

actual fun formatDateTimeDisplay(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}


actual fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")
