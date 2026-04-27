package com.shared.core.platform

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String get() = UIDevice.currentDevice.systemName()
    override val osVersion: String get() = UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()