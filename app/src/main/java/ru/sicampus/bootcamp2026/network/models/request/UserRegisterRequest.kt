package ru.sicampus.bootcamp2026.network.models.request


data class UserRegisterRequest(
    val email: String,
    val password: String,
    val displayName: String
)