package com.tomiappdevelopment.raintosunanimation.presentation.uiModules

import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

data class CloudState(
    val id: Int, // Optional if you're managing multiple clouds
    private val dropIdCounter: AtomicInteger = AtomicInteger(0),
    private val bufferA: SnapshotStateList<Raindrop> = mutableStateListOf(),
    private val bufferB: SnapshotStateList<Raindrop> = mutableStateListOf(),
    var activeBuffer: MutableState<Boolean> = mutableStateOf(true),
    val visibleDrops: SnapshotStateList<Raindrop> = mutableStateListOf(),
    var opacity: MutableFloatState = mutableFloatStateOf(1f),
    val startXAxis: Float = -5f,
    val endXAxis: Float = 10f,
    val startYAxis: Float = -5f,
    val endYAxis: Float = 10f,
) {
    val currentBuffer: SnapshotStateList<Raindrop>
        get() = if (activeBuffer.value) bufferA else bufferB

    fun spawnDrop(cloudX: Float,cloudY: Float, cloudWidth: Float) {
        val drop = Raindrop(
            id = dropIdCounter.incrementAndGet(),
            startX = cloudX + Random.Default.nextFloat() * cloudWidth,
            startY = cloudY,
            size = Random.Default.nextFloat() * 20f + 10f,
            blur = Random.Default.nextFloat() * 2f,
            opacity = opacity.floatValue
        )
        currentBuffer.add(drop)
        visibleDrops.add(drop)
    }

    fun cleanup() {
        val inactive = if (activeBuffer.value) bufferB else bufferA
        inactive.removeAll { !it.visible }
        visibleDrops.removeAll { !it.visible }
        activeBuffer.value = !activeBuffer.value
    }


    fun setOpacity(value: Float) {
        opacity.floatValue = value.coerceIn(0f, 1f)
    }
}