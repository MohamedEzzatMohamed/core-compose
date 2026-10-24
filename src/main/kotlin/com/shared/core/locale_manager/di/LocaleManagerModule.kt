package com.shared.core.locale_manager.di

import com.shared.core.locale_manager.LocaleHelper
import com.shared.core.locale_manager.LocaleManagerImpl
import com.shared.core.locale_manager.settings_lang_storage.SettingsLangStorage
import com.shared.core.locale_manager.settings_lang_storage.createSettingsStorage
import org.koin.dsl.module

val localeManagerModule = module {

    single<SettingsLangStorage> {
        createSettingsStorage(preferencesStorage = get())
    }

    single<LocaleHelper> {
        LocaleManagerImpl(storage = get(), coroutineScope = get())
    }
}