@file:JvmName("TimeUtilsAndroid")

package com.shared.core.utils

import com.shared.core.app_logger.AppLogger
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

actual fun parseDateTimeToMillis(dateTimeStr: String, inputPattern: String): Long? {
    val trimmed = dateTimeStr.trim()
    // Check if it's likely UTC (contains Z or T)
    val isUtc = trimmed.endsWith("Z") || trimmed.contains("T")

    // Normalize ISO 8601 format
    val normalized = trimmed
        .replace("T", " ")
        .removeSuffix("Z")
        .replace(Regex("\\.\\d+$"), "") // strip fractional seconds

    return try {
        val formatter = SimpleDateFormat(inputPattern, Locale.ENGLISH)
        if (isUtc) {
            formatter.timeZone = TimeZone.getTimeZone("UTC")
        }
        formatter.parse(normalized)?.time
    } catch (e: Exception) {
        AppLogger.e("Date cannot parse $e")
        try {
            val altFormatter = SimpleDateFormat(inputPattern, Locale.ENGLISH)
            if (isUtc) {
                altFormatter.timeZone = TimeZone.getTimeZone("UTC")
            }
            altFormatter.parse(normalized)?.time
        } catch (e2: Exception) {
            AppLogger.e("Date cannot parse $e2")
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
