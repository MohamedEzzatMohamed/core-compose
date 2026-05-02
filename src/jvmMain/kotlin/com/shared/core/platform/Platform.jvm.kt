package com.shared.core.platform

class JVMPlatform : Platform {
    override val platformType: PlatformTypeEnum get() = PlatformTypeEnum.DESKTOP
    override val osVersion: String get() = System.getProperty("java.version")
}

actual fun getPlatform(): Platform = JVMPlatform()