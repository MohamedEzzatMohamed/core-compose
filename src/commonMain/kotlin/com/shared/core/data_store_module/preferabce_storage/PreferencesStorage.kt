package com.shared.core.data_store_module.preferabce_storage

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface PreferencesStorage {


    suspend fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, default: Boolean): Flow<Boolean?>

    suspend fun setInteger(key: String, value: Int)
    fun getInteger(key: String, default: Int): Flow<Int?>

    suspend fun setFloat(key: String, value: Float)
    fun getFloat(key: String, default: Float): Flow<Float?>

    suspend fun setString(key: String, value: String)
    fun getString(key: String, default: String): Flow<String?>

    suspend fun <T> saveObject(key: String, value: T, serializer: KSerializer<T>)

    fun <T> getObject(key: String, serializer: KSerializer<T>): Flow<T?>

    suspend fun removeBooleanByKey(key: String)

    suspend fun removeIntegerByKey(key: String)

    suspend fun removeFloatByKey(key: String)

    suspend fun removeStringByKey(key: String)

    suspend fun clearAll()
}