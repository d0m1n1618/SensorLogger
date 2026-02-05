package com.example.sensorlogger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensorlogger.data.MeasurementEntity
import com.example.sensorlogger.data.MeasurementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailsUiState(
    val item: MeasurementEntity? = null,
    val error: String? = null
)

class DetailsViewModel(
    private val repo: MeasurementRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(DetailsUiState())
    val ui: StateFlow<DetailsUiState> = _ui

    fun load(id: Long) = viewModelScope.launch {
        val item = repo.getById(id)
        _ui.value = if (item != null) DetailsUiState(item = item) else DetailsUiState(error = "Nie znaleziono pomiaru")
    }

    fun delete(measurementId: Long) = viewModelScope.launch {
        try {
            repo.deleteById(measurementId)
        } catch (e: Exception) {
            _ui.update { it.copy(error = e.message ?: "Nie udało się usunąć pomiaru") }
        }
    }

}
