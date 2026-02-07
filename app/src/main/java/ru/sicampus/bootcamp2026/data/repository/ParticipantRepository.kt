package ru.sicampus.bootcamp2026.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.ParticipantRole
import ru.sicampus.bootcamp2026.network.models.request.ParticipantAddRequest
import ru.sicampus.bootcamp2026.network.models.response.ParticipantResponse
import java.util.UUID

class ParticipantRepository(
    private val travoRepository: TravoRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getParticipants(tripId: UUID): Result<List<ParticipantResponse>> {
        return travoRepository.getParticipants(tripId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addParticipant(
        tripId: UUID,
        userId: UUID,
        role: ParticipantRole = ParticipantRole.MEMBER
    ): Result<ParticipantResponse> {
        val request = ParticipantAddRequest(userId = userId, role = role)
        return travoRepository.addParticipant(tripId, request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun removeParticipant(tripId: UUID, userId: UUID): Result<Unit> {
        return travoRepository.removeParticipant(tripId, userId)
    }

    suspend fun updateParticipantRole(
        tripId: UUID,
        userId: UUID,
        newRole: ParticipantRole
    ): Result<Unit> {
        // Сначала удаляем участника
        val removeResult = removeParticipant(tripId, userId)
        if (removeResult.isFailure) {
            return removeResult
        }

        // Затем добавляем с новой ролью
        return try {
            val addResult = addParticipant(tripId, userId, newRole)
            if (addResult.isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(addResult.exceptionOrNull() ?: Exception("Ошибка при обновлении роли"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getParticipant(tripId: UUID, userId: UUID): Result<ParticipantResponse?> {
        return getParticipants(tripId).map { participants ->
            participants.find { it.userId == userId }
        }
    }

    fun getParticipantRole(participant: ParticipantResponse): ParticipantRole {
        return participant.role
    }

    fun canModifyParticipants(currentUserRole: ParticipantRole, targetUserRole: ParticipantRole): Boolean {
        return when (currentUserRole) {
            ParticipantRole.OWNER -> targetUserRole != ParticipantRole.OWNER
            ParticipantRole.ADMIN -> targetUserRole == ParticipantRole.MEMBER
            ParticipantRole.MEMBER -> false
        }
    }
}