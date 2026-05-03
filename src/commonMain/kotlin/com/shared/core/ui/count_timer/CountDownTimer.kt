package com.shared.core.ui.count_timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

private const val COUNT_DOWN_TIMER = 1000L

@Composable
fun CountdownTimer(
    totalTime: Long,
    interval: Long = COUNT_DOWN_TIMER,
    onRemainingTime: @Composable (Long) -> Unit,
    onTimerFinish: () -> Unit
) {
    var remainingTime by remember(totalTime) {
        mutableLongStateOf(totalTime)
    }

    LaunchedEffect(totalTime) {
        remainingTime = totalTime

        while (remainingTime > 0) {
            delay(interval)

            remainingTime = (remainingTime - interval)
                .coerceAtLeast(0L)
        }

        onTimerFinish()
    }

    onRemainingTime(remainingTime)
}