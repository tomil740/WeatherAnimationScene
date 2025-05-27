package com.tomiappdevelopment.raintosunanimation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class WeatherSceneColors(
    val SunnyYellow :Color = Color(0xFFFFEB3B),
    val SunsetOrange :Color=Color(0xFFFF9800),

    val SunnySkyTop:Color= Color(0xFFFFF59D),
    val RainySkyTop:Color = Color(0xFF0D1B2A),

    val SunnySkyBottom:Color = Color(0xFF81D4FA),
    val RainySkyBottom :Color= Color(0xFF1B263B)
)

val LocalWeatherSceneColors = staticCompositionLocalOf {
    WeatherSceneColors(

    )
}