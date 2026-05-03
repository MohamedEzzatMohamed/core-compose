package com.holouly.core.ui_component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExposedDropdownMenuBoxComponent(
    dropdownList: List<T>,
    exposedDropdownMenuBackground: Color,
    exposedDropdownMenuShape: Shape= RoundedCornerShape(12.dp),
    exposedDropdownMenuBoxContent: @Composable ExposedDropdownMenuBoxScope.(Boolean) -> Unit,
    optionItemContent: @Composable (option: T) -> Unit,
    onClick: (option: T) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {

        exposedDropdownMenuBoxContent(expanded)

        ExposedDropdownMenu(
            modifier = Modifier
                .clip(exposedDropdownMenuShape)
                .background(exposedDropdownMenuBackground),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropdownList.forEach { option ->
                DropdownMenuItem(
                    text = { optionItemContent(option) },
                    onClick = {
                        expanded = false
                        onClick(option)
                    }
                )
            }
        }
    }
}
