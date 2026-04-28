package com.shared.core.data_store_module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorageImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val dataStorePlatformModule = module {
    single<DataStore<Preferences>> {
        val documentsDirectory = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String

        val dataStoreFile = "$documentsDirectory/${dataStoreFileName}"

        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            produceFile = { dataStoreFile.toPath() },
            scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        )
    }

    single<PreferencesStorage> { PreferencesStorageImpl(dataStore = get()) }
}