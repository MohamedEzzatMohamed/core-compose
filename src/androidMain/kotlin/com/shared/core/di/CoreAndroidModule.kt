package com.shared.core.di

import com.shared.core.cash_drawer.di.cashDrawerModule
import org.koin.dsl.module

val coreAndroidModule = module {
    includes(cashDrawerModule)
}