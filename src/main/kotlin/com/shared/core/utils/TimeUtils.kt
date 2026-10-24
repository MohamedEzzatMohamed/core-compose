package com.shared.core.utils

import com.shared.core.app_logger.AppLogger
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(kotlin.time.ExperimentalTime::class)
fun formatUtcDate(dateString: String): String {
    val instant = Instant.parse(input = dateString)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val day = localDateTime.day.toString()
    val month = localDateTime.month.number
    val time = localDateTime.time
    return "$day-$month-${localDateTime.year} $time"
}

fun formatDateTime(dateTimeString: String?, inputPattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (dateTimeString.isNullOrBlank()) return ""
    return try {
        val millis = parseDateTimeToMillis(dateTimeStr = dateTimeString, inputPattern = inputPattern)
        if (millis != null) {
            formatDateTimeDisplay(timestampMillis = millis)
        } else {
            dateTimeString
        }
    } catch (e: Exception) {
        AppLogger.e(message = "Cannot be format date time", throwable = e)
        dateTimeString
    }
}

fun currentTimeMillis(): Long = System.currentTimeMillis()

fun formatDateTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    return formatter.format(Date(timestamp))
}

fun formatDecimal(value: Double, precision: Int): String {
    return String.format(Locale.US, "%.${precision}f", value)
}

fun formatDecimalWithCommas(value: Double, precision: Int): String {
    return String.format(Locale.US, "%,.${precision}f", value)
}

fun formatPaddedInt(value: Int, width: Int): String {
    return String.format(Locale.US, "%0${width}d", value)
}

fun parseDateTimeToMillis(dateTimeStr: String, inputPattern: String): Long? {
    val trimmed = dateTimeStr.trim()
    val isUtc = trimmed.endsWith("Z") || trimmed.contains("T")

    val normalized = trimmed
        .replace("T", " ")
        .removeSuffix("Z")
        .replace(Regex("\\.\\d+$"), "")

    return try {
        val formatter = SimpleDateFormat(inputPattern, Locale.ENGLISH)
        if (isUtc) {
            formatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
        }
        formatter.parse(normalized)?.time
    } catch (e: Exception) {
        AppLogger.e("Date cannot parse $e")
        try {
            val altFormatter = SimpleDateFormat(inputPattern, Locale.ENGLISH)
            if (isUtc) {
                altFormatter.timeZone = java.util.TimeZone.getTimeZone("UTC")
            }
            altFormatter.parse(normalized)?.time
        } catch (e2: Exception) {
            AppLogger.e("Date cannot parse $e2")
            null
        }
    }
}

fun elapsedSecondsSince(timestampMillis: Long): Long {
    return ((currentTimeMillis() - timestampMillis) / 1000).coerceAtLeast(0)
}

fun formatTime12Hour(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}

fun formatDate(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}

fun formatDateTimeDisplay(timestampMillis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)
    return formatter.format(Date(timestampMillis))
}

fun getTodayDateString(): String = formatDate(currentTimeMillis())

fun urlEncode(value: String): String = URLEncoder.encode(value, "UTF-8")
