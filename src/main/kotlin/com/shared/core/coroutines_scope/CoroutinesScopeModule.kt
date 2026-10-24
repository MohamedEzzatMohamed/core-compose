package com.shared.core.coroutines_scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val CoroutinesScopeModule = module {
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}