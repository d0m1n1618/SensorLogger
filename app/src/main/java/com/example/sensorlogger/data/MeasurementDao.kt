package com.example.sensorlogger.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM measurements ORDER BY timestampMillis DESC")
    fun observeAll(): Flow<List<MeasurementEntity>>

    @Query("SELECT * FROM measurements WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MeasurementEntity?

    @Insert
    suspend fun insert(entity: MeasurementEntity): Long

    @Query("DELETE FROM measurements")
    suspend fun deleteAll()

    @Query("DELETE FROM measurements WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Delete
    suspend fun delete(entity: MeasurementEntity)
}
