package com.shared.core.locale_manager.app_locale_enum

enum class AppLanguageEnum(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val isRtl: Boolean
) {
    ENGLISH(code = "en", displayName = "English", nativeName = "English", isRtl = false),
    ARABIC(code = "ar", displayName = "Arabic", nativeName = "العربية", isRtl = true),
    URDU(code = "ur", displayName = "Urdu", nativeName = "أردو", isRtl = true),
    TURKISH(code = "tr", displayName = "Turkish", nativeName = "Turkish", isRtl = false);

    companion object {
        fun fromCode(code: String?): AppLanguageEnum {
            return entries.find { it.code == code } ?: ENGLISH
        }
    }
}