package com.shared.core.platform


expect fun getPlatform(): Platform

val platform get() = getPlatform()

interface Platform {
    val name: String

    val osVersion: String
}