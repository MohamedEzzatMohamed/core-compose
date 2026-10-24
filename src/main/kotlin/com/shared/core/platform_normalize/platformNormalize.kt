package com.shared.core.platform_normalize

import java.text.Normalizer

fun platformNormalize(text: String): String {
    return Normalizer.normalize(text, Normalizer.Form.NFKC)
}

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
