package ru.sicampus.bootcamp2026.ui.participant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sicampus.bootcamp2026.data.repository.ParticipantRepository
import ru.sicampus.bootcamp2026.network.models.ParticipantRole
import ru.sicampus.bootcamp2026.network.models.response.ParticipantResponse
import java.util.UUID

class ParticipantViewModel : ViewModel(), KoinComponent {
    private val participantRepository: ParticipantRepository by inject()

    private val _participantsState = mutableStateOf<List<ParticipantResponse>>(emptyList())
    val participantsState: State<List<ParticipantResponse>> = _participantsState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun loadParticipants(tripId: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            participantRepository.getParticipants(tripId).fold(
                onSuccess = { participants ->
                    _participantsState.value = participants
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка загрузки участников: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun addParticipant(tripId: UUID, userId: UUID, role: ParticipantRole = ParticipantRole.MEMBER) {
        viewModelScope.launch {
            _isLoading.value = true
            participantRepository.addParticipant(tripId, userId, role).fold(
                onSuccess = {
                    loadParticipants(tripId)
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка добавления участника: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun removeParticipant(tripId: UUID, userId: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            participantRepository.removeParticipant(tripId, userId).fold(
                onSuccess = {
                    loadParticipants(tripId)
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка удаления участника: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun updateParticipantRole(tripId: UUID, userId: UUID, newRole: ParticipantRole) {
        viewModelScope.launch {
            _isLoading.value = true
            participantRepository.updateParticipantRole(tripId, userId, newRole).fold(
                onSuccess = {
                    loadParticipants(tripId)
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка обновления роли: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}