package ru.sicampus.bootcamp2026.ui.profie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sicampus.bootcamp2026.data.repository.UserRepository
import ru.sicampus.bootcamp2026.network.models.response.UserResponse

class ProfileViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()

    private val _userState = mutableStateOf<UserResponse?>(null)
    val userState: State<UserResponse?> = _userState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isEditing = mutableStateOf(false)
    val isEditing: State<Boolean> = _isEditing

    private val _displayName = mutableStateOf("")
    val displayName: State<String> = _displayName

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getCurrentUser().fold(
                onSuccess = { user ->
                    _userState.value = user
                    _displayName.value = user.displayName
                    _email.value = user.email
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка загрузки профиля: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun startEditing() {
        _isEditing.value = true
    }

    fun cancelEditing() {
        _isEditing.value = false
        _userState.value?.let { user ->
            _displayName.value = user.displayName
            _email.value = user.email
        }
    }

    fun updateProfile(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.updateProfile(
                displayName = _displayName.value.takeIf { it != _userState.value?.displayName },
                email = _email.value.takeIf { it != _userState.value?.email }
            ).fold(
                onSuccess = {
                    loadUserProfile()
                    _isEditing.value = false
                    onSuccess()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка обновления профиля: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun changePassword(currentPassword: String, newPassword: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.changePassword(currentPassword, newPassword).fold(
                onSuccess = {
                    _errorMessage.value = "Пароль успешно изменен"
                    onSuccess()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка смены пароля: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun setDisplayName(name: String) {
        _displayName.value = name
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun clearError() {
        _errorMessage.value = null
    }
}