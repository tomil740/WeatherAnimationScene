package com.tomiappdevelopment.raintosunanimation.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants
import com.tomiappdevelopment.raintosunanimation.ui.theme.LocalWeatherSceneColors
import kotlinx.coroutines.launch

@Composable
fun AnimatedSunScene(
    isRainView: Boolean,
    screenHeightPx: Float,
    modifier: Modifier = Modifier
) {
    val weatherColors = LocalWeatherSceneColors.current

    val startY = screenHeightPx * WeatherAnimationConstants.SunStartYOffsetRatio
    val endY = -screenHeightPx * WeatherAnimationConstants.SunEndYOffsetRatio

    val sunOffsetY = remember { Animatable(startY) }
    val sunAlpha = remember { Animatable(0f) }

    val sunColor by animateColorAsState(
        targetValue = if (!isRainView) Color(weatherColors.SunnyYellow.value) else Color(weatherColors.SunsetOrange.value),
        animationSpec = tween(durationMillis = WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION)
    )

    val sunOpacity by animateFloatAsState(
        targetValue = if (isRainView) 0f else 1f,
        animationSpec = tween(durationMillis = WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION)
    )

    LaunchedEffect(isRainView) {
        if (!isRainView) {
            launch {
                sunAlpha.animateTo(1f, tween(WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION))
            }
            launch {
                sunOffsetY.animateTo(endY, tween(WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION,
                    easing = FastOutSlowInEasing))
            }
        } else {
            launch {
                sunAlpha.animateTo(0f, tween(WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION))
            }
            launch {
                sunOffsetY.animateTo(startY, tween(WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION, easing = FastOutSlowInEasing))
            }
        }
    }

    AnimatedSun(
        offsetY = sunOffsetY.value,
        opacity = sunOpacity,
        color = sunColor,
        modifier = modifier
            .zIndex(if (isRainView) -1f else 100f)
    )
}
