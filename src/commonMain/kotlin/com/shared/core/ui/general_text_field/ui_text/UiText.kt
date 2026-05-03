package com.shared.core.ui.general_text_field.ui_text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiText {
    class StringRes(val resId: StringResource, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringRes -> stringResource(resId, *args)
        }
    }
}