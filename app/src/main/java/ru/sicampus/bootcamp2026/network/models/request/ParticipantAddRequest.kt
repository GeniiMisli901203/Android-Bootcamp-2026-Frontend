package ru.sicampus.bootcamp2026.network.models.request
import ru.sicampus.bootcamp2026.network.models.ParticipantRole

import java.util.UUID

data class ParticipantAddRequest(
    val userId: UUID,
    val role: ParticipantRole = ParticipantRole.MEMBER
)