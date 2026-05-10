package com.shared.core.di

import com.shared.core.cash_drawer.di.iosCashDrawerModule
import org.koin.dsl.module

val coreIosModule = module {
    includes(iosCashDrawerModule)
}