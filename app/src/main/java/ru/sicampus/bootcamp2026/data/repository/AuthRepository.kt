package ru.sicampus.bootcamp2026.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.sicampus.bootcamp2026.data.local.PreferencesManager
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.request.UserLoginRequest
import ru.sicampus.bootcamp2026.network.models.request.UserRegisterRequest

class AuthRepository(
    private val travoRepository: TravoRepository,
    private val preferencesManager: PreferencesManager
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun login(email: String, password: String): Result<Unit> {
        val request = UserLoginRequest(
            email = email,
            password = password
        )

        val result = travoRepository.login(request)

        return result.map { response ->
            preferencesManager.saveToken(response.accessToken)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun register(email: String, displayName: String, password: String): Result<Unit> {
        val request = UserRegisterRequest(
            email = email,
            password = password,
            displayName = displayName
        )

        val result = travoRepository.register(request)

        return result.map { user ->
            login(email, password)
        }
    }

    fun logout() {
        preferencesManager.clearToken()
    }

    fun isUserLoggedIn(): Boolean {
        return preferencesManager.isLoggedIn()
    }

    fun observeAuthState(): Flow<Boolean> = flow {
        emit(preferencesManager.isLoggedIn())
    }
}