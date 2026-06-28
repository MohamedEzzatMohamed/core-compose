package com.shared.core.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSTimeZone
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneWithName


actual fun currentTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}


actual fun formatDateTime(timestamp: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(timestamp / 1000.0)
    val formatter = NSDateFormatter()
    formatter.setDateFormat("yyyy-MM-dd HH:mm:ss")
    return formatter.stringFromDate(date)
}


actual fun formatDecimal(value: Double, precision: Int): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = precision.toULong()
    formatter.maximumFractionDigits = precision.toULong()
    formatter.locale = NSLocale("en_US")
    formatter.decimalSeparator = "."
    formatter.usesGroupingSeparator = false
    return formatter.stringFromNumber(NSNumber(value)) ?: "0"
}


actual fun formatDecimalWithCommas(value: Double, precision: Int): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    formatter.minimumFractionDigits = precision.toULong()
    formatter.maximumFractionDigits = precision.toULong()
    formatter.locale = NSLocale("en_US")
    formatter.decimalSeparator = "."
    formatter.groupingSeparator = ","
    formatter.usesGroupingSeparator = true
    return formatter.stringFromNumber(NSNumber(value)) ?: "0"
}


actual fun formatPaddedInt(value: Int, width: Int): String {
    val str = value.toString()
    return if (str.length >= width) str else str.padStart(width, '0')
}


actual fun parseDateTimeToMillis(dateTimeStr: String, inputPattern: String): Long? {
    val trimmed = dateTimeStr.trim()
    // Check if it's likely UTC (contains Z or T)
    val isUtc = trimmed.endsWith("Z") || trimmed.contains("T")

    // Normalize ISO 8601 format (e.g. "2026-02-01T15:04:28.000000Z") to "yyyy-MM-dd HH:mm:ss"
    val normalized = trimmed
        .replace("T", " ")
        .removeSuffix("Z")
        .replace(Regex("\\.\\d+$"), "") // strip fractional seconds
    val formatter = NSDateFormatter()
    if (isUtc) {
        formatter.timeZone = NSTimeZone.timeZoneWithName("UTC")!!
    }
    formatter.setDateFormat(inputPattern)
    var date = formatter.dateFromString(normalized)
    if (date == null) {
        formatter.setDateFormat(inputPattern)
        date = formatter.dateFromString(normalized)
    }
    return date?.timeIntervalSince1970?.let { (it * 1000).toLong() }
}

actual fun formatTime12Hour(timestampMillis: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(timestampMillis / 1000.0)
    val formatter = NSDateFormatter()
    formatter.setDateFormat("hh:mm a")
    formatter.locale = NSLocale("en_US")
    return formatter.stringFromDate(date)
}


actual fun formatDate(timestampMillis: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(timestampMillis / 1000.0)
    val formatter = NSDateFormatter()
    formatter.setDateFormat("yyyy-MM-dd")
    formatter.locale = NSLocale("en_US")
    return formatter.stringFromDate(date)
}


actual fun formatDateTimeDisplay(timestampMillis: Long): String {
    val date = NSDate.dateWithTimeIntervalSince1970(timestampMillis / 1000.0)
    val formatter = NSDateFormatter()
    formatter.setDateFormat("dd MMM yyyy, hh:mm a")
    formatter.locale = NSLocale("en_US")
    return formatter.stringFromDate(date)
}


actual fun urlEncode(value: String): String {
    // Simple URL encoding implementation for iOS
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"
    return buildString {
        for (char in value) {
            if (char in allowedChars) {
                append(char)
            } else {
                // Encode the character
                val bytes = char.toString().encodeToByteArray()
                for (byte in bytes) {
                    append('%')
                    append(((byte.toInt() shr 4) and 0xF).toString(16).uppercase())
                    append((byte.toInt() and 0xF).toString(16).uppercase())
                }
            }
        }
    }
}
