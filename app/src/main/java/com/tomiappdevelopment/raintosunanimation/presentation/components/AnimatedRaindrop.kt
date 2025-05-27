package com.tomiappdevelopment.raintosunanimation.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tomiappdevelopment.raintosunanimation.R
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants
import com.tomiappdevelopment.raintosunanimation.presentation.core.rememberScreenMetrics
import com.tomiappdevelopment.raintosunanimation.presentation.uiModules.Raindrop
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AnimatedRaindrop(
    raindrop: Raindrop,
) {
    val screenMetrics = rememberScreenMetrics()

    val yOffset = remember { Animatable( raindrop.startY* WeatherAnimationConstants.RAINDROP_Y_MULTIPLIER ) }
    val endYOffset = screenMetrics.screenHeightPx * WeatherAnimationConstants.RAINDROP_Y_MULTIPLIER
    val alphaAnim = remember { Animatable(0f) }
    LaunchedEffect(raindrop.id) {

        launch {
            // Drop movement downwards
            yOffset.animateTo(
                targetValue = (endYOffset),
                animationSpec = tween(
                    durationMillis = raindrop.duration * Random.nextInt(5, 11),
                    easing = LinearEasing
                )
            )
        }


        launch {
            alphaAnim.animateTo(
                targetValue = raindrop.opacity,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )


            alphaAnim.animateTo(
                targetValue = -1f,
                animationSpec = tween(
                    durationMillis = raindrop.duration ,
                    easing = LinearEasing
                )
            )

        }

        delay((raindrop.duration + 300).toLong())

        if(alphaAnim.value < 0f){
            raindrop.visible = false
        }
    }

    Image(
        painter = painterResource(R.drawable.water_droplet),
        contentDescription = "Raindrop",
        modifier = Modifier
            .offset { IntOffset(raindrop.startX.toInt(), yOffset.value.toInt()) }
            .size(raindrop.size.dp)
            .graphicsLayer(alpha = alphaAnim.value)
            .blur(radius = raindrop.blur.dp)
    )
}
