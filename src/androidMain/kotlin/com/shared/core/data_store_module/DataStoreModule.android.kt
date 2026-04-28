package com.shared.core.data_store_module

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorageImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

actual val dataStorePlatformModule = module {
    single {
        val context = androidContext()
        val dataStoreFile = File(context.filesDir, dataStoreFileName)

        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            produceFile = { dataStoreFile.absolutePath.toPath() },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    single<PreferencesStorage> { PreferencesStorageImpl(dataStore = get()) }
}