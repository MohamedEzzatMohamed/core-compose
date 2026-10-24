package com.shared.core.ui.current_time_state

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.shared.core.utils.currentTimeMillis
import com.shared.core.utils.formatTime12Hour

@Composable
fun RowScope.CurrentTimeState(
    modifier: Modifier = Modifier,
    @DrawableRes timeIcon: Int,
    timeIconTint: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current,
) {
    var currentTimeText by remember { mutableStateOf(value = formatTime12Hour(timestampMillis = currentTimeMillis())) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            kotlinx.coroutines.delay(60_000L) // Update every minute
            currentTimeText = formatTime12Hour(currentTimeMillis())
        }
    }

    Icon(
        modifier = modifier,
        painter = painterResource(id = timeIcon),
        contentDescription = "Location Icon",
        tint = timeIconTint
    )

    Spacer(modifier = Modifier.width(2.dp))

    Text(
        text = currentTimeText,
        style = style,
        fontSize = fontSize
    )
}
