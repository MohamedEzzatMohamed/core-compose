package com.shared.core.locale_manager

import com.shared.core.locale_manager.app_locale_enum.AppLanguageEnum
import kotlinx.coroutines.flow.StateFlow

interface LocaleHelper {

    val currentLanguage: StateFlow<AppLanguageEnum>

    val appLanguageList : List<AppLanguageEnum>
    suspend fun changeLanguage(language: AppLanguageEnum)

}