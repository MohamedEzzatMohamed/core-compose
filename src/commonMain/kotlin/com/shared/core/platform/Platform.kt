package com.shared.core.platform


expect fun getPlatform(): Platform

val platform get() = getPlatform()

interface Platform {
    val platformType: PlatformTypeEnum

    val osVersion: String
}