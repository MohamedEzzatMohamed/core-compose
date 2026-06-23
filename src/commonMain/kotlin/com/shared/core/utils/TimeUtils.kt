package com.shared.core.utils

import com.shared.core.app_logger.AppLogger
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant


fun formatUtcDate(dateString: String): String {
    val instant = Instant.parse(input = dateString)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val day = localDateTime.day.toString()
    val month = localDateTime.month.number
    val time = localDateTime.time
    return "$day-$month-${localDateTime.year} $time"
}


fun formatDateTime(dateTimeString: String?): String {
    if (dateTimeString.isNullOrBlank()) return ""
    return try {
        val millis = parseDateTimeToMillis(dateTimeStr = dateTimeString)
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

/**
 * Platform-specific function to get current time in milliseconds since epoch.
 * This is used instead of System.currentTimeMillis() for multiplatform compatibility.
 */
expect fun currentTimeMillis(): Long

/**
 * Format a timestamp to a date-time string in format "yyyy-MM-dd HH:mm:ss"
 */
expect fun formatDateTime(timestamp: Long): String

/**
 * Format a double with specified decimal precision.
 * Uses US locale (dot as decimal separator) for consistency.
 * Example: formatDecimal(123.456, 2) -> "123.46"
 */
expect fun formatDecimal(value: Double, precision: Int): String

/**
 * Format a double with specified decimal precision and thousands separator (comma).
 * Uses US locale (dot as decimal separator, comma as thousands separator).
 * Example: formatDecimalWithCommas(1234.56, 2) -> "1,234.56"
 */
expect fun formatDecimalWithCommas(value: Double, precision: Int): String

/**
 * Format an integer with leading zeros to specified width.
 * Example: formatPaddedInt(5, 2) -> "05"
 */
expect fun formatPaddedInt(value: Int, width: Int): String

/**
 * Parse a datetime string in format "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd HH:mm"
 * to milliseconds since epoch. Returns null if parsing fails.
 */
expect fun parseDateTimeToMillis(dateTimeStr: String): Long?

/**
 * Calculate elapsed seconds between a timestamp and now.
 */
fun elapsedSecondsSince(timestampMillis: Long): Long {
    return ((currentTimeMillis() - timestampMillis) / 1000).coerceAtLeast(0)
}

/**
 * Format a timestamp to 12-hour time format "hh:mm a" (e.g., "02:30 PM").
 * Uses English locale for consistent display.
 */
expect fun formatTime12Hour(timestampMillis: Long): String

/**
 * Format a timestamp to date format "yyyy-MM-dd".
 * Uses English locale for consistent display.
 */
expect fun formatDate(timestampMillis: Long): String

/**
 * Format a timestamp to "dd MMM yyyy, hh:mm a" format.
 * Uses English locale for consistent display.
 * Example: "07 Dec 2025, 02:30 PM"
 */
expect fun formatDateTimeDisplay(timestampMillis: Long): String

/**
 * Get today's date in "yyyy-MM-dd" format.
 * Uses English locale for consistent display.
 */
fun getTodayDateString(): String = formatDate(currentTimeMillis())


/**
 * URL encode a string for safe use in URLs.
 */
expect fun urlEncode(value: String): String
