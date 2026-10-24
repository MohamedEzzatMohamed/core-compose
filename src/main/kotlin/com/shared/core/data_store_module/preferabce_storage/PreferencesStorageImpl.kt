package com.shared.core.data_store_module.preferabce_storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shared.core.app_logger.AppLogger
import com.shared.core.extensions.decodeFromString
import com.shared.core.extensions.encodeToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer

class PreferencesStorageImpl(private val dataStore: DataStore<Preferences>) : PreferencesStorage {


    override suspend fun setBoolean(key: String, value: Boolean) {
        val prefKey = booleanPreferencesKey(key)
        dataStore.edit { preferences -> preferences[prefKey] = value }
    }

    override fun getBoolean(key: String, default: Boolean): Flow<Boolean?> {
        val prefKey = booleanPreferencesKey(key)
        return dataStore.data.map { preferences -> preferences[prefKey] ?: default }
    }

    override suspend fun setInteger(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        dataStore.edit { preferences -> preferences[prefKey] = value }
    }

    override fun getInteger(key: String, default: Int): Flow<Int?> {
        val prefKey = intPreferencesKey(key)
        return dataStore.data.map { preferences -> preferences[prefKey] ?: default }
    }

    override suspend fun setFloat(key: String, value: Float) {
        val prefKey = floatPreferencesKey(key)
        dataStore.edit { preferences -> preferences[prefKey] = value }
    }

    override fun getFloat(key: String, default: Float): Flow<Float?> {
        val prefKey = floatPreferencesKey(key)
        return dataStore.data.map { preferences -> preferences[prefKey] ?: default }
    }

    override suspend fun setString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        dataStore.edit { preferences -> preferences[prefKey] = value }
    }

    override fun getString(key: String, default: String): Flow<String?> {
        val prefKey = stringPreferencesKey(key)
        return dataStore.data.map { preferences -> preferences[prefKey] ?: default }
    }

    override suspend fun <T> saveObject(key: String, value: T, serializer: KSerializer<T>) {
        val stringValue = withContext(Dispatchers.Default) {
            serializer.encodeToString(value = value)
        }
        setString(key, stringValue)
    }


    override fun <T> getObject(key: String, serializer: KSerializer<T>) =
        getString(key, "").map { jsonString ->
            if (jsonString.isNullOrBlank()) return@map null
            runCatching {
                serializer.decodeFromString(jsonString = jsonString)
            }.onFailure { e ->
                AppLogger.e(
                    message = "Failed to decode object for key: $key. Error: ${e.message}",
                    throwable = e,
                )
            }.getOrNull()
        }.flowOn(Dispatchers.Default)


    override suspend fun removeBooleanByKey(key: String) {
        val prefKey = booleanPreferencesKey(key)
        dataStore.edit { preferences -> preferences.remove(prefKey) }

    }

    override suspend fun removeIntegerByKey(key: String) {
        val prefKey = intPreferencesKey(key)
        dataStore.edit { preferences -> preferences.remove(prefKey) }

    }

    override suspend fun removeFloatByKey(key: String) {
        val prefKey = floatPreferencesKey(key)
        dataStore.edit { preferences -> preferences.remove(prefKey) }
    }


    override suspend fun removeStringByKey(key: String) {
        val prefKey = stringPreferencesKey(key)
        dataStore.edit { preferences -> preferences.remove(prefKey) }
    }


    override suspend fun clearAll() {
        dataStore.edit { preferences -> preferences.clear() }
    }
}