package com.shared.core.data_store_module

import org.koin.core.module.Module

internal const val dataStoreFileName = "dice.preferences_pb"

expect val dataStorePlatformModule: Module
