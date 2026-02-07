package ru.sicampus.bootcamp2026.network.models.response

import ru.sicampus.bootcamp2026.network.models.ParticipantRole
import java.time.Instant
import java.util.UUID

data class ParticipantResponse(
    val tripId: UUID,          // новое поле
    val userId: UUID,
    val displayName: String,   // было userName
    val email: String,         // было userEmail
    val role: ParticipantRole,
    val joinedAt: Instant      // было String
)