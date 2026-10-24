package com.shared.core.ui.general_text_field.ui_text

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    class StringRes(@androidx.annotation.StringRes val resId: Int, vararg val args: Any) : UiText()

    data class DynamicString(val value: String) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringRes -> stringResource(resId, *args)
        }
    }
}
