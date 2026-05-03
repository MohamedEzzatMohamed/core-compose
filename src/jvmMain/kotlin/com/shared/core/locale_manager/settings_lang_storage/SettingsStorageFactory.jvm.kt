package com.shared.core.locale_manager.settings_lang_storage

import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.locale_manager.app_locale_enum.AppLanguageEnum
import kotlinx.coroutines.flow.Flow
import java.util.Locale

actual fun getSystemLanguage(): String = Locale.getDefault().language

actual fun createSettingsStorage(preferencesStorage: PreferencesStorage): SettingsLangStorage {
    return SettingsDesktopLangStorageImpl(preferencesStorage = preferencesStorage)
}


class SettingsDesktopLangStorageImpl(
    private val preferencesStorage: PreferencesStorage
) : SettingsLangStorage {

    override suspend fun saveLanguage(languageCode: String) {
        preferencesStorage.setString(APP_LANGUAGE, languageCode)
    }

    override fun getLanguage(): Flow<String?> = preferencesStorage.getString(
        key = APP_LANGUAGE, default = AppLanguageEnum.ENGLISH.code
    )

    override fun applyLanguage(languageCode: String) {
        Locale.setDefault(Locale.forLanguageTag(languageCode))
    }
}