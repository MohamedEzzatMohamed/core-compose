package com.shared.core.bases.base_usecase.validation_state

import com.shared.core.ui.general_text_field.ui_text.UiText


data class ValidationState(
    val successful: Boolean,
    val errorMessage: UiText? = null
)