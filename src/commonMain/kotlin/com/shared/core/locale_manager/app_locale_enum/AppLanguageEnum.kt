package com.shared.core.locale_manager.app_locale_enum

enum class AppLanguageEnum(
    val code: String,
    val isRtl: Boolean
) {
    ENGLISH(code = "en", isRtl = false),
    ARABIC(code = "ar", isRtl = true),
    TURKISH(code = "tr", isRtl = false),
    URDU(code = "ur", isRtl = true);

    companion object {
        fun fromCode(code: String?): AppLanguageEnum {
            return entries.find { it.code == code } ?: ENGLISH
        }
    }
}