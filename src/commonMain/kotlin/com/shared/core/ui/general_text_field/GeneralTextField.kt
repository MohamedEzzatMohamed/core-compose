package com.shared.core.ui.general_text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shared.core.validation_method.isNumber
import com.shared.core.ui.general_text_field.ui_text.UiText


@Composable
fun GeneralTextField(
    modifier: Modifier,
    placeholder: String,
    text: String = "",
    textStyle: TextStyle = TextStyle.Default,
    textAlignment: Alignment.Vertical = Alignment.CenterVertically,
    cursorBrush: Brush = SolidColor(Color.Black),
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unFocusedColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(size = 8.dp),
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: UiText? = null,
    readOnly: Boolean = false,
    isError: Boolean = false,
    isVisible: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLine: Int = 1,
) {
    val isKeyboardTypeNumber =
        keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val colorBorder = if (isError) {
        MaterialTheme.colorScheme.error
    } else if (isFocused) {
        focusedColor
    } else {
        unFocusedColor
    }

    Column {
        BasicTextField(
            value = if (isKeyboardTypeNumber) if (isNumber(value = text)) text else text else text,
            onValueChange = { if (isKeyboardTypeNumber) if (isNumber(value = it)) onValueChange(it) else onValueChange(it) },
            textStyle = textStyle,
            maxLines = maxLine,
            singleLine = singleLine,
            interactionSource = interactionSource,
            readOnly = readOnly,
            visualTransformation = if (keyboardType == KeyboardType.Password) {
                if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            cursorBrush = cursorBrush,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = textAlignment, modifier = modifier.border(
                        width = 1.dp,
                        shape = shape,
                        color = colorBorder
                    ).background(
                        color = backgroundColor,
                        shape = shape
                    ).focusRequester(focusRequester)
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    Box(modifier = Modifier.weight(1.0f).padding(vertical = 16.dp)) {
                        if (text.isEmpty()) {
                            Text(text = placeholder, style = textStyle)
                        }
                        Box(modifier = Modifier.fillMaxWidth()) { innerTextField() }
                    }
                    if (trailingIcon != null) {
                        trailingIcon()
                    } else {
                        Spacer(modifier = Modifier.padding(all = 8.dp))
                    }
                }
            },
        )
        errorMessage?.asString()?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}