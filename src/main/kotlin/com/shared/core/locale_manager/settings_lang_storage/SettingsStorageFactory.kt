package com.shared.core.locale_manager.settings_lang_storage

import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.locale_manager.app_locale_enum.AppLanguageEnum
import kotlinx.coroutines.flow.Flow
import java.util.Locale

const val APP_LANGUAGE = "APP_LANGUAGE"

fun getSystemLanguage(): String = Locale.getDefault().language

fun createSettingsStorage(preferencesStorage: PreferencesStorage): SettingsLangStorage {
    return SettingsAndroidLangStorageImpl(preferencesStorage = preferencesStorage)
}

class SettingsAndroidLangStorageImpl(private val preferencesStorage: PreferencesStorage) :
    SettingsLangStorage {

    override suspend fun saveLanguage(languageCode: String) {
        preferencesStorage.setString(APP_LANGUAGE, languageCode)
    }

    override fun getLanguage(): Flow<String?> = preferencesStorage.getString(
        key = APP_LANGUAGE, default = AppLanguageEnum.ENGLISH.code
    )

    override fun applyLanguage(languageCode: String) {
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)
    }
}
