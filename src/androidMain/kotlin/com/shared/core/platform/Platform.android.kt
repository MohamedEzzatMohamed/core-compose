package com.shared.core.platform

import android.os.Build

class AndroidPlatform : Platform {
    override val platformType: PlatformTypeEnum get() = PlatformTypeEnum.ANDROID

    override val osVersion: String get() =  "${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()