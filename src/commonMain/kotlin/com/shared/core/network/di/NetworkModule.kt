package com.shared.core.network.di

import com.shared.core.network.ApiClient
import org.koin.dsl.module


val networkModule = module {
    single { ApiClient(get()) }
}