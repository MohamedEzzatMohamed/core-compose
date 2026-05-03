package com.shared.core.data_store_module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
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
    single<DataStore<Preferences>> { createDataStore(context = androidContext()) }
    single<PreferencesStorage> { PreferencesStorageImpl(dataStore = get()) }
}

private var dataStoreInstance: DataStore<Preferences>? = null
fun createDataStore(context: Context): DataStore<Preferences> {
    val appContext = context.applicationContext

    return dataStoreInstance ?: synchronized(appContext) {
        dataStoreInstance ?: PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                File(appContext.filesDir, dataStoreFileName).absolutePath.toPath()
            },
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        ).also { dataStoreInstance = it }
    }
}
