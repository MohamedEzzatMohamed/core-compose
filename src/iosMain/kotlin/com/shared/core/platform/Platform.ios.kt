package com.shared.core.platform

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val platformType: PlatformTypeEnum get() = PlatformTypeEnum.IOS
    override val osVersion: String get() = UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()