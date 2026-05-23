package com.shared.core.ui.exposed_dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shared.core.ui.general_text_field.GeneralTextField
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExposedDropdownMenuBoxComponent(
    modifier: Modifier = Modifier,
    searchModifier: Modifier = Modifier,
    searchLeadingBoxModifier: Modifier = Modifier,
    searchIconModifier: Modifier = Modifier,
    dropdownList: List<T>,
    isSearchable: Boolean = false,
    searchQueryValue: String = "",
    searchPlaceholder: String = "Search...",
    searchTextStyle: TextStyle = TextStyle.Default,
    searchPlaceholderTextStyle: TextStyle = TextStyle.Default,
    searchInputShape: Shape = RoundedCornerShape(size = 8.dp),
    exposedDropdownMenuBackground: Color,
    exposedDropdownMenuShape: Shape = RoundedCornerShape(size = 12.dp),
    searchIconResource: DrawableResource? = null,
    searchBackgroundColor: Color = Color(0xFFFFFFFF),
    searchIconTint: Color = Color(0xFFBBBBBB),
    searchFocusedColor: Color = Color(0xFFD9D9D9),
    searchUnFocusedColor: Color = Color(0xFFD9D9D9),
    exposedDropdownMenuBoxContent: @Composable ExposedDropdownMenuBoxScope.(Boolean) -> Unit,
    optionItemContent: @Composable (option: T) -> Unit,
    onSearchQueryChange: (String) -> Unit = {},
    onClick: (option: T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        exposedDropdownMenuBoxContent(expanded)

        ExposedDropdownMenu(
            modifier = modifier
                .clip(exposedDropdownMenuShape)
                .background(exposedDropdownMenuBackground),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            if (isSearchable) {

                SearchTextFiled(
                    modifier = searchModifier,
                    leadingBoxModifier = searchLeadingBoxModifier,
                    iconModifier = searchIconModifier,
                    searchQueryValue = searchQueryValue,
                    searchPlaceholder = searchPlaceholder,
                    textStyle = searchTextStyle,
                    placeholderTextStyle = searchPlaceholderTextStyle,
                    iconResource = searchIconResource,
                    searchBackgroundColor = searchBackgroundColor,
                    searchIconTint = searchIconTint,
                    searchFocusedColor = searchFocusedColor,
                    searchUnFocusedColor = searchUnFocusedColor,
                    searchInputShape = searchInputShape,
                    onSearchQueryChange = onSearchQueryChange,
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = Color(0xFFD9D9D9).copy(alpha = 0.5f)
                )
            }


            dropdownList.forEach { option ->
                DropdownMenuItem(
                    text = { optionItemContent(option) },
                    onClick = {
                        expanded = false
                        onClick(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@Composable
fun SearchTextFiled(
    modifier: Modifier = Modifier,
    leadingBoxModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    searchQueryValue: String,
    searchPlaceholder: String,
    iconResource: DrawableResource?,
    searchBackgroundColor: Color,
    searchIconTint: Color,
    searchFocusedColor: Color,
    searchUnFocusedColor: Color,
    searchInputShape: Shape,
    textStyle: TextStyle ,
    placeholderTextStyle: TextStyle,
    onSearchQueryChange: (String) -> Unit = {},
) {
    GeneralTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .wrapContentHeight(),
        placeholder = searchPlaceholder,
        text = searchQueryValue,
        textStyle = textStyle,
        placeholderTextStyle = placeholderTextStyle,
        onValueChange = onSearchQueryChange,
        leadingIcon = {
            Box(modifier = leadingBoxModifier.padding(horizontal = 10.dp)) {
                iconResource?.let {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "Search",
                        tint = searchIconTint,
                        modifier = iconModifier.size(16.dp),
                    )
                }
            }
        },
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,

        shape = searchInputShape,
        backgroundColor = searchBackgroundColor,
        focusedColor = searchFocusedColor,
        unFocusedColor = searchUnFocusedColor,
        singleLine = true,
    )
}