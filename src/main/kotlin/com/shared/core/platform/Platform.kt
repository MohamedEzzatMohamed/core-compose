package com.shared.core.platform

import android.os.Build

interface Platform {
    val platformType: PlatformTypeEnum
    val osVersion: String
}

class AndroidPlatform : Platform {
    override val platformType: PlatformTypeEnum get() = PlatformTypeEnum.ANDROID
    override val osVersion: String get() = "${Build.VERSION.SDK_INT}"
}

fun getPlatform(): Platform = AndroidPlatform()

@get:JvmName("getPlatformProperty")
val platform: Platform get() = getPlatform()
