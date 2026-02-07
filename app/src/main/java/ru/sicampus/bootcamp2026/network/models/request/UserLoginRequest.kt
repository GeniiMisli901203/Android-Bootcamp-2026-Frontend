package ru.sicampus.bootcamp2026.network.models.request

data class UserLoginRequest(
    val email: String,
    val password: String
)