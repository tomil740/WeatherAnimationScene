package com.tomiappdevelopment.raintosunanimation.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomiappdevelopment.raintosunanimation.presentation.components.CloudRainView
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants
import kotlinx.coroutines.launch

@Composable
fun AnimatedSun(
    offsetY: Float,
    opacity: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .size(180.dp)
            .graphicsLayer {
                translationY = offsetY
                alpha = opacity
            }
    ) {
        val center = Offset(size.width / 2, size.height / 2)

        // Glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                center = center,
                radius = size.minDimension * WeatherAnimationConstants.SunGlowRadiusRatio
            ),
            center = center,
            radius = size.minDimension * WeatherAnimationConstants.SunGlowRadiusRatio
        )

        // Core
        drawCircle(
            color = color,
            radius = size.minDimension / 2,
            center = center
        )
    }
}
