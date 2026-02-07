package ru.sicampus.bootcamp2026.network.API


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.sicampus.bootcamp2026.network.models.request.UserLoginRequest
import ru.sicampus.bootcamp2026.network.models.request.UserRegisterRequest
import ru.sicampus.bootcamp2026.network.models.response.JwtLoginResponse
import ru.sicampus.bootcamp2026.network.models.response.UserResponse

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: UserRegisterRequest): Response<UserResponse>

    @POST("auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<JwtLoginResponse>
}