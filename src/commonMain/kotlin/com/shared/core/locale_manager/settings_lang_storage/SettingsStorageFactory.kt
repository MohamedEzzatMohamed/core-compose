package com.shared.core.locale_manager.settings_lang_storage

import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage


const val APP_LANGUAGE = "APP_LANGUAGE"

expect fun getSystemLanguage(): String

expect fun createSettingsStorage(preferencesStorage: PreferencesStorage): SettingsLangStorage