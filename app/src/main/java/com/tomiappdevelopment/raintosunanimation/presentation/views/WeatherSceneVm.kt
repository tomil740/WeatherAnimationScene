package com.tomiappdevelopment.raintosunanimation.presentation.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tomiappdevelopment.raintosunanimation.presentation.uiModules.CloudState
import kotlin.random.Random

class WeatherSceneVm : ViewModel() {
    private val _clouds = mutableStateListOf<CloudState>()
    val clouds: List<CloudState> get() = _clouds

    private val _isRainView: MutableState<Boolean> =  mutableStateOf(true)
    val isRainView get() = _isRainView.value

    fun onViewTransition(){
        _isRainView.value = !isRainView
    }


    fun generateClouds(screenWidthPx: Float, screenHeightPx: Float) {
        val random = Random.Default
        val cloudCount = 22
        val topCloudPercentage = 0.3f

        val xTotalRange = screenWidthPx * 0.2f
        val yTotalRange = screenHeightPx * 0.2f

        val xRanges = listOf(
            -50f to xTotalRange / 3f,
            xTotalRange / 3f to xTotalRange * 2f / 3f,
            xTotalRange * 2f / 3f to xTotalRange
        )

        val yRanges = listOf(
            0f to yTotalRange / 3f,
            yTotalRange / 3f to yTotalRange * 2f / 3f,
            yTotalRange * 2f / 3f to yTotalRange
        )

        _clouds.clear()

        repeat(cloudCount) { index ->
            val xRangeIndex = index % xRanges.size
            val yRangeIndex = (index / (cloudCount / yRanges.size)).coerceAtMost(yRanges.lastIndex)

            val xRange = xRanges[xRangeIndex]
            val yRange = yRanges[yRangeIndex]

            var x = xRange.first + random.nextFloat() * (xRange.second - xRange.first)
            var y = yRange.first + random.nextFloat() * (yRange.second - yRange.first)

            if (index < (cloudCount * topCloudPercentage).toInt()) {
                y = random.nextFloat() * (screenHeightPx * 0.015f)
            }


            val opacity = listOf(0.2f,0.3f,0.4f, 0.5f).random()

            _clouds.add(
                CloudState(
                    id = index,
                    startXAxis = x,
                    endXAxis = x + random.nextFloat() * 50f,
                    startYAxis = y,
                    endYAxis = y + random.nextFloat() * 30f
                ).apply { setOpacity(opacity) }
            )
        }
    }



}
