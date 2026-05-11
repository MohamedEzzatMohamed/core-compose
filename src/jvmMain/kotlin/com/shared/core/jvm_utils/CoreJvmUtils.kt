package com.shared.core.jvm_utils

import java.io.File

object CoreJvmUtils {

    fun getAppDataDir(appName  : String): File {
        val osName = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appName = appName
        val baseDir = when {
            osName.contains("win") -> {

                val localAppData = System.getenv("LOCALAPPDATA") ?: "$userHome\\AppData\\Local"
                File(localAppData, appName)
            }

            osName.contains("mac") -> File(userHome, "Library/Application Support/$appName")

            else -> File(userHome, ".$appName".lowercase())

        }
        if (!baseDir.exists()) baseDir.mkdirs()
        return baseDir
    }
}