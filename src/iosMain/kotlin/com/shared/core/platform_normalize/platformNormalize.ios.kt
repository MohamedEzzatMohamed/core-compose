package com.shared.core.platform_normalize

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.precomposedStringWithCompatibilityMapping

@OptIn(BetaInteropApi::class)
actual fun platformNormalize(text: String): String {
    val nsString = NSString.create(string = text)
    return nsString.precomposedStringWithCompatibilityMapping()
}