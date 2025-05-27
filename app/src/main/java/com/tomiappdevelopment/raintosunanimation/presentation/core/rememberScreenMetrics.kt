package com.tomiappdevelopment.raintosunanimation.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

data class ScreenMetrics(
    val screenWidthPx: Float,
    val screenHeightPx: Float
)

@Composable
fun rememberScreenMetrics(): ScreenMetrics {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val widthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val heightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    return remember(widthPx, heightPx) {
        ScreenMetrics(widthPx, heightPx)
    }
}
