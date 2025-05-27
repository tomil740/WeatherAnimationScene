package com.tomiappdevelopment.raintosunanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tomiappdevelopment.raintosunanimation.ui.theme.RainToSunAnimationTheme
import com.tomiappdevelopment.raintosunanimation.presentation.views.WeatherSceneScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RainToSunAnimationTheme {
                WeatherSceneScreen(
                )
            }
        }
    }
}

