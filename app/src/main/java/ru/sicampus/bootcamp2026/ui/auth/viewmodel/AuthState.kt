package ru.sicampus.bootcamp2026.ui.auth.viewmodel

sealed interface AuthState {
    object Idle : AuthState
    object Loading : AuthState
    object Success : AuthState
    data class Error(val message: String) : AuthState
}