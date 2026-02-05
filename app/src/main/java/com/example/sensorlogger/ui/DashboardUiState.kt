package com.example.sensorlogger.ui

import com.example.sensorlogger.data.MeasurementEntity

data class DashboardUiState(
    val isMeasuring: Boolean = false,
    val measurements: List<MeasurementEntity> = emptyList(),
    val error: String? = null
) {
    val count: Int get() = measurements.size
    val avgNoise: Float get() = measurements.map { it.noiseDb }.average().toFloatOrNull() ?: 0f
    val avgAccel: Float get() = measurements.map { it.accelPeak }.average().toFloatOrNull() ?: 0f
}

private fun Double.toFloatOrNull(): Float? = if (this.isNaN()) null else this.toFloat()
