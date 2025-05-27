package com.tomiappdevelopment.raintosunanimation.presentation.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomiappdevelopment.raintosunanimation.presentation.components.AnimatedSunScene
import com.tomiappdevelopment.raintosunanimation.presentation.components.CloudRainView
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants
import com.tomiappdevelopment.raintosunanimation.presentation.core.rememberScreenMetrics
import com.tomiappdevelopment.raintosunanimation.ui.theme.LocalWeatherSceneColors

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun WeatherSceneScreen(
    weatherSceneVm: WeatherSceneVm = viewModel(),
    modifier: Modifier = Modifier) {

    val weatherColors = LocalWeatherSceneColors.current
    val screenMetrics = rememberScreenMetrics()

    LaunchedEffect(Unit) {
        weatherSceneVm.generateClouds(screenMetrics.screenWidthPx, screenMetrics.screenHeightPx)
    }

    val backgroundColorTop by animateColorAsState(
        targetValue = if (!weatherSceneVm.isRainView) Color(weatherColors.SunnySkyTop.value) else Color(weatherColors.RainySkyTop.value),
        animationSpec = tween(durationMillis = WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION)
    )
    val backgroundColorBottom by animateColorAsState(
        targetValue = if (!weatherSceneVm.isRainView) Color(weatherColors.SunnySkyBottom.value) else Color(weatherColors.RainySkyBottom.value),
        animationSpec = tween(durationMillis = WeatherAnimationConstants.GLOBAL_SCENE_ANIMATION_DURATION)
    )

        Box(
        modifier = modifier.
        clickable{
            weatherSceneVm.onViewTransition() }
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(backgroundColorTop, backgroundColorBottom)
                )
            )
    ) {
            weatherSceneVm.clouds.forEach {
                key(it.id) {
                    CloudRainView(
                        isRainView = weatherSceneVm.isRainView,
                        cloudState = it
                    )
                }
            }


           AnimatedSunScene(
                    isRainView = weatherSceneVm.isRainView,
                    screenHeightPx = screenMetrics.screenHeightPx,
                    modifier=  Modifier.align(Alignment.BottomCenter)
                )

            AnimatedContent(
                targetState = weatherSceneVm.isRainView,
                transitionSpec = {
                    fadeIn(tween(400)) with fadeOut(tween(400))
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 12.dp)
            ) { isRain ->
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = if (isRain) "🌧️ Rain Mode" else "☀️ Sunny Mode",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "🌦️/🌞 Tap to toggle",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }



        }
    }

