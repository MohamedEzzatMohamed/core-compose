package com.shared.core.locale_manager.settings_lang_storage

import kotlinx.coroutines.flow.Flow

interface SettingsLangStorage {
    suspend fun saveLanguage(languageCode: String)
    fun getLanguage(): Flow<String?>

    fun applyLanguage(languageCode: String)
}