package com.shared.core.data_store_module

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorageImpl
import com.shared.core.jvm_utils.CoreJvmUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath
import org.koin.dsl.module
import java.io.File

actual val dataStorePlatformModule = module {
    single<DataStore<Preferences>> { createDesktopDataStore() }
    single<PreferencesStorage> { PreferencesStorageImpl(dataStore = get()) }
}

private var dataStoreInstance: DataStore<Preferences>? = null

fun createDesktopDataStore(): DataStore<Preferences> {
    return dataStoreInstance ?: synchronized(Unit) {
        dataStoreInstance ?: run {
            val appDir = CoreJvmUtils.getAppDataDir(appName = "Core-Cmp")
            if (!appDir.exists()) appDir.mkdirs()

            val dataStoreFile = File(appDir, dataStoreFileName)

            PreferenceDataStoreFactory.createWithPath(
                corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
                produceFile = { dataStoreFile.absolutePath.toPath() },
                scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            ).also { dataStoreInstance = it }
        }
    }
}