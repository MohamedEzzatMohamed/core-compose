package com.shared.core.platform_normalize


expect fun platformNormalize(text: String): String


fun normalizeArabicText(text: String): String {
    if (text.isBlank()) return ""
    

    val normalizedUnicode = platformNormalize(text)

    return normalizedUnicode
        .lowercase()
        .trim()
        .replace("أ", "ا")
        .replace("إ", "ا")
        .replace("آ", "ا")
        .replace("ة", "ه")
        .replace("ى", "ي")
}