package com.example.sensorlogger.di

import android.content.Context
import androidx.room.Room
import com.example.sensorlogger.data.AppDatabase
import com.example.sensorlogger.data.MeasurementRepository

class AppModule(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "sensor_logger.db"
    ).build()

    val repository: MeasurementRepository = MeasurementRepository(db.measurementDao())
}
