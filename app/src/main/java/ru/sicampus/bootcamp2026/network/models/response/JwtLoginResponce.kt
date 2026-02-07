package ru.sicampus.bootcamp2026.network.models.response

data class JwtLoginResponse(
    val accessToken: String,
    val user: UserResponse
)