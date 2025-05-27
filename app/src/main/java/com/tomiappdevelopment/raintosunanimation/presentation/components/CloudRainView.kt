package com.tomiappdevelopment.raintosunanimation.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomiappdevelopment.raintosunanimation.R
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants.CLOUD_DRIFT_DURATION
import com.tomiappdevelopment.raintosunanimation.presentation.core.WeatherAnimationConstants.CLOUD_FADE_DURATION
import com.tomiappdevelopment.raintosunanimation.presentation.uiModules.CloudState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun CloudRainView(
    cloudState: CloudState,
    isRainView: Boolean,
    modifier: Modifier = Modifier
) {


    val cloudOffsetX by rememberInfiniteTransition().animateFloat(
        initialValue = cloudState.startXAxis,
        targetValue = cloudState.endXAxis,
        animationSpec = infiniteRepeatable(
            animation = tween(CLOUD_DRIFT_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val cloudOffsetY by rememberInfiniteTransition().animateFloat(
        initialValue = cloudState.startYAxis,
        targetValue = cloudState.endYAxis,
        animationSpec = infiniteRepeatable(
            animation = tween(CLOUD_DRIFT_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Remember the target opacity (used when going back to rain view)
    val baseOpacity = remember { cloudState.opacity.floatValue }

    // Target opacity based on isRainView flag
    val targetOpacity = rememberUpdatedState(if (isRainView) baseOpacity else {if (Random.nextBoolean()){1f}else{0f}})

    // Animate opacity change
    val animatedOpacity by animateFloatAsState(
        targetValue = targetOpacity.value,
        animationSpec = tween(durationMillis = CLOUD_FADE_DURATION, easing = FastOutSlowInEasing),
        label = "cloud_opacity"
    )

    LaunchedEffect(animatedOpacity) {
        cloudState.setOpacity(animatedOpacity)
    }

    Box(modifier = modifier) {
        // Cloud Image
        Image(
            painter = painterResource(R.drawable.cloud),
            contentDescription = "Cloud",
            modifier = Modifier
                .offset(x = cloudOffsetX.dp, y = cloudOffsetY.dp)
                .graphicsLayer(alpha = animatedOpacity)
        )


            cloudState.visibleDrops.filter { it.visible }.forEach { drop ->
                key(drop.id) {
                    AnimatedRaindrop(raindrop = drop)
                }
            }

    }


    // Launching drop spawner
    LaunchedEffect(cloudState,isRainView) {
        val maxActiveDrops = 100

        launch {

            delay(1000)

            while (isRainView) {
                delay(200)
                if (cloudState.visibleDrops.size < maxActiveDrops) {
                    val dropX = cloudOffsetX * 2f
                    val dropY = cloudOffsetY
                    cloudState.spawnDrop(dropX,dropY,800f)
                    delay(200)
                } else {
                    delay(600) // wait longer if full
                }
            }
        }

        launch {
            while(true) {
                delay(2000)  // every 2 seconds clean up
                cloudState.cleanup()
            }
        }
    }


}
