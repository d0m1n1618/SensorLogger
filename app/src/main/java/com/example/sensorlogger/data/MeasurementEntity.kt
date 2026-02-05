package com.example.sensorlogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val timestampMillis: Long,
    val latitude: Double?,
    val longitude: Double?,
    val locationAccuracyM: Float?,
    val accelPeak: Float,
    val noiseDb: Float
)
