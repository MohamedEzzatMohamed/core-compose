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

private const val PREFERENCES_TEMP = "dice.preferences_pb.tmp"

actual val dataStorePlatformModule = module {
    single<DataStore<Preferences>> {
        val appDir = getAppDataDir()
        if (!appDir.exists()) appDir.mkdirs()

        val tmpFile = File(appDir, PREFERENCES_TEMP)
        if (tmpFile.exists()) tmpFile.delete()

        val dataStoreFile = File(appDir, dataStoreFileName)

        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { dataStoreFile.absolutePath.toPath() },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    single<PreferencesStorage> { PreferencesStorageImpl(dataStore = get()) }
}


private fun getAppDataDir(): File {
    val osName = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    return when {
        osName.contains("win") -> {
            val localAppData = System.getenv("LOCALAPPDATA") ?: "$userHome\\AppData\\Local"
            File(localAppData, "babu")
        }

        osName.contains("mac") -> File(userHome, "Library/Application Support/babu")
        else -> File(userHome, ".babu")
    }
}