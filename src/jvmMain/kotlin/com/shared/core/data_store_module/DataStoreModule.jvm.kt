package com.shared.core.data_store_module

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorage
import com.shared.core.data_store_module.preferabce_storage.PreferencesStorageImpl
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
            val appDir = getAppDataDir()
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


private fun getAppDataDir(): File {
    val osName = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    val appName = "Babu"
    val baseDir = when {
        osName.contains("win") -> {

            val localAppData = System.getenv("LOCALAPPDATA") ?: "$userHome\\AppData\\Local"
            File(localAppData, appName)
        }

        osName.contains("mac") -> File(userHome, "Library/Application Support/$appName")

        else -> File(userHome, ".$appName".lowercase())

    }
    if (!baseDir.exists()) baseDir.mkdirs()
    return baseDir
}