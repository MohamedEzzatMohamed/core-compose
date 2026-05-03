package com.shared.core.locale_manager

import com.shared.core.locale_manager.app_locale_enum.AppLanguageEnum
import com.shared.core.locale_manager.settings_lang_storage.SettingsLangStorage
import com.shared.core.locale_manager.settings_lang_storage.getSystemLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


class LocaleManagerImpl(
    private val storage: SettingsLangStorage,
    coroutineScope: CoroutineScope
) : LocaleHelper {


    private val _currentLanguage = MutableStateFlow(value = AppLanguageEnum.ENGLISH)
    override val currentLanguage: StateFlow<AppLanguageEnum> = _currentLanguage

    override val appLanguageList: List<AppLanguageEnum> get() = AppLanguageEnum.entries

    init {
        coroutineScope.launch { initializeLanguage() }
    }

    private suspend fun initializeLanguage() {
        storage.getLanguage().distinctUntilChanged().collectLatest { savedCode ->
            val lang = AppLanguageEnum.fromCode(savedCode ?: getSystemLanguage())
            storage.applyLanguage(lang.code)
            _currentLanguage.value = lang
        }
    }

    override suspend fun changeLanguage(language: AppLanguageEnum) {
        storage.saveLanguage(language.code)
        storage.applyLanguage(language.code)
        _currentLanguage.value = language
    }
}
