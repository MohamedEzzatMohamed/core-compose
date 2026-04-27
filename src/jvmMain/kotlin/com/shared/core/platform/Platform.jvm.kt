package com.shared.core.platform

class JVMPlatform : Platform {
    override val name: String get() = "Desktop"
    override val osVersion: String get() = System.getProperty("java.version")
}

actual fun getPlatform(): Platform = JVMPlatform()