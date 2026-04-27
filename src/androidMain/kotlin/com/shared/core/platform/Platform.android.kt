package com.shared.core.platform

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String get() = "Android"

    override val osVersion: String get() =  "${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()