package ru.sicampus.bootcamp2026.ui.trips

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.threeten.bp.LocalDate
import ru.sicampus.bootcamp2026.data.repository.TripRepository
import ru.sicampus.bootcamp2026.network.models.response.TripResponse
import java.util.UUID


class TripViewModel : ViewModel(), KoinComponent {
    private val tripRepository: TripRepository by inject()

    private val _tripsState = MutableStateFlow<List<TripResponse>>(emptyList())
    val tripsState: StateFlow<List<TripResponse>> = _tripsState.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _currentTrip = MutableStateFlow<TripResponse?>(null)
    val currentTrip: StateFlow<TripResponse?> = _currentTrip.asStateFlow()

    fun loadTrips() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            tripRepository.getTrips().fold(
                onSuccess = { trips ->
                    _tripsState.value = trips
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка загрузки поездок: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun loadTrip(tripId: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            tripRepository.getTrip(tripId).fold(
                onSuccess = { trip ->
                    _currentTrip.value = trip
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка загрузки поездки: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun createTrip(
        name: String,
        description: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        currency: String = "RUB",
        onSuccess: (UUID) -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            tripRepository.createTrip(
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                currency = currency
            ).fold(
                onSuccess = { response ->
                    loadTrips()
                    onSuccess(response.id)
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка создания поездки: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun updateTrip(
        tripId: UUID,
        name: String? = null,
        description: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        currency: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            tripRepository.updateTrip(
                id = tripId,
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                currency = currency
            ).fold(
                onSuccess = { updatedTrip ->
                    _currentTrip.value = updatedTrip
                    loadTrips()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка обновления поездки: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun deleteTrip(tripId: UUID, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            tripRepository.deleteTrip(tripId).fold(
                onSuccess = {
                    loadTrips()
                    onSuccess()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка удаления поездки: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}