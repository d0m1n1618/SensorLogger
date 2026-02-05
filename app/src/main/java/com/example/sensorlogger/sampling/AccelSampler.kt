package com.example.sensorlogger.sampling

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.delay
import kotlin.math.sqrt

class AccelSampler(private val context: Context) {

    suspend fun samplePeak(durationMs: Long = 1500L): Float {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: return 0f

        var peak = 0f
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val mag = sqrt(x * x + y * y + z * z)
                if (mag > peak) peak = mag
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME)
        return try {
            delay(durationMs)
            peak
        } finally {
            sm.unregisterListener(listener)
        }
    }
}
