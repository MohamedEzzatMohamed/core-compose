package com.shared.core.locale_manager.settings_lang_storage

import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.locale_manager.app_locale_enum.AppLanguageEnum
import kotlinx.coroutines.flow.Flow
import platform.Foundation.NSUserDefaults
import platform.Foundation.currentLocale
import platform.Foundation.languageCode


actual fun getSystemLanguage(): String = platform.Foundation.NSLocale.currentLocale.languageCode

actual fun createSettingsStorage(preferencesStorage: PreferencesStorage): SettingsLangStorage {
    return SettingsIosLangStorageImpl(preferencesStorage = preferencesStorage)
}

private const val APP_LANGUAGES = "AppleLanguages"

class SettingsIosLangStorageImpl(
    private val preferencesStorage: PreferencesStorage
) : SettingsLangStorage {

    override suspend fun saveLanguage(languageCode: String) {
        preferencesStorage.setString(APP_LANGUAGE, languageCode)
    }

    override fun getLanguage(): Flow<String?> = preferencesStorage.getString(
        key = APP_LANGUAGE, default = AppLanguageEnum.ENGLISH.code
    )

    override fun applyLanguage(languageCode: String) {
        NSUserDefaults.standardUserDefaults.setObject(listOf(languageCode), forKey = APP_LANGUAGES)
    }
}

