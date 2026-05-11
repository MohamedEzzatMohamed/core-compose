package com.shared.core.di

import com.shared.core.cash_drawer.di.desktopCashDrawerModule
import org.koin.dsl.module

val coreDeskTopModule = module {
    includes(desktopCashDrawerModule)
}