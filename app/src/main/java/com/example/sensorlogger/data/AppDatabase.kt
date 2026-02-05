package com.example.sensorlogger.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MeasurementEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}
