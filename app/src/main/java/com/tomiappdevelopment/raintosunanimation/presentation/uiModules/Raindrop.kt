package com.tomiappdevelopment.raintosunanimation.presentation.uiModules

data class Raindrop(
    val id: Int,
    val startX: Float,
    val startY: Float,
    val size: Float,
    val blur: Float,
    var opacity: Float,
    val duration: Int = 4000,
    var visible: Boolean = true, // controls UI visibility
    val createdAt: Long = System.currentTimeMillis()

)