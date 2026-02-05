package com.example.sensorlogger.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensorlogger.data.MeasurementEntity
import com.example.sensorlogger.data.MeasurementRepository
import com.example.sensorlogger.sampling.AccelSampler
import com.example.sensorlogger.sampling.LocationSampler
import com.example.sensorlogger.sampling.NoiseSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val context: Context,
    private val repo: MeasurementRepository
) : ViewModel() {

    private val locationSampler = LocationSampler(context)
    private val accelSampler = AccelSampler(context)
    private val noiseSampler = NoiseSampler()

    private val _ui = MutableStateFlow(DashboardUiState())
    val ui: StateFlow<DashboardUiState> = _ui.asStateFlow()

    init {
        viewModelScope.launch {
            repo.observeAll().collect { list ->
                _ui.update { it.copy(measurements = list) }
            }
        }
    }

    fun reset() = viewModelScope.launch { repo.reset() }

    fun measureOnce() = viewModelScope.launch {
        _ui.update { it.copy(isMeasuring = true, error = null) }
        try {
            val now = System.currentTimeMillis()

            val loc = locationSampler.getCurrent()
            val accelPeak = accelSampler.samplePeak(1500L)
            val noiseDb = noiseSampler.sampleDb(1500L)

            repo.add(
                MeasurementEntity(
                    timestampMillis = now,
                    latitude = loc?.latitude,
                    longitude = loc?.longitude,
                    locationAccuracyM = loc?.accuracy,
                    accelPeak = accelPeak,
                    noiseDb = noiseDb
                )
            )
        } catch (e: Exception) {
            _ui.update { it.copy(error = e.message ?: "Błąd pomiaru") }
        } finally {
            _ui.update { it.copy(isMeasuring = false) }
        }
    }
}
