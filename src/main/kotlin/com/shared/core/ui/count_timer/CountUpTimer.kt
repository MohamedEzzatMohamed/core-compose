package com.shared.core.ui.count_timer


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay


@Composable
fun CountUpTimer(timeElapsedInput: Long = 0L, onTimeElapsed: @Composable (Long) -> Unit) {

    var timeElapsed by remember { mutableLongStateOf(timeElapsedInput) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            timeElapsed++
        }
    }
    onTimeElapsed(timeElapsed)
}