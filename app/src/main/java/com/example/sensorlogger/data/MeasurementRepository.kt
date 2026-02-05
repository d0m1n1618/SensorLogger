package com.example.sensorlogger.data

import kotlinx.coroutines.flow.Flow

class MeasurementRepository(private val dao: MeasurementDao) {
    fun observeAll(): Flow<List<MeasurementEntity>> = dao.observeAll()

    suspend fun getById(id: Long): MeasurementEntity? = dao.getById(id)

    suspend fun add(entity: MeasurementEntity): Long = dao.insert(entity)

    suspend fun reset() = dao.deleteAll()

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
