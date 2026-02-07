package ru.sicampus.bootcamp2026.network.models.response

import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val displayName: String
)