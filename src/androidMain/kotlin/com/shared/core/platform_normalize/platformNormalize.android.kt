package com.shared.core.platform_normalize

import java.text.Normalizer

actual fun platformNormalize(text: String): String {
    return Normalizer.normalize(text, Normalizer.Form.NFKC)
}