package ru.sicampus.bootcamp2026.network

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ru.sicampus.bootcamp2026.network.config.RetrofitClient
import ru.sicampus.bootcamp2026.network.error.ApiError
import ru.sicampus.bootcamp2026.network.models.request.*
import ru.sicampus.bootcamp2026.network.models.response.*
import com.google.gson.Gson
import retrofit2.Response
import ru.sicampus.bootcamp2026.network.API.AuthApi
import ru.sicampus.bootcamp2026.network.API.ExpenseApi
import ru.sicampus.bootcamp2026.network.API.ParticipantApi
import ru.sicampus.bootcamp2026.network.API.RouteApi
import ru.sicampus.bootcamp2026.network.API.SettlementApi
import ru.sicampus.bootcamp2026.network.API.TripApi
import java.util.UUID
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class TravoRepository(private val context: Context) {
    private val authApi: AuthApi by lazy {
        RetrofitClient.getClient(context).create(AuthApi::class.java)
    }

    private val tripApi: TripApi by lazy {
        RetrofitClient.getClient(context).create(TripApi::class.java)
    }

    private val expenseApi: ExpenseApi by lazy {
        RetrofitClient.getClient(context).create(ExpenseApi::class.java)
    }

    private val routeApi: RouteApi by lazy {
        RetrofitClient.getClient(context).create(RouteApi::class.java)
    }

    private val participantApi: ParticipantApi by lazy {
        RetrofitClient.getClient(context).create(ParticipantApi::class.java)
    }

    private val settlementApi: SettlementApi by lazy {
        RetrofitClient.getClient(context).create(SettlementApi::class.java)
    }

    private suspend fun <T> handleResponse(response: Response<T>): Result<T> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Пустой ответ"))
            } else {
                val errorBody = response.errorBody()?.string()
                val error = if (!errorBody.isNullOrEmpty()) {
                    try {
                        val apiError = Gson().fromJson(errorBody, ApiError::class.java)
                        Exception(apiError.error)
                    } catch (e: Exception) {
                        Exception("Ошибка: ${response.code()} - ${response.message()}")
                    }
                } else {
                    Exception("Ошибка: ${response.code()}")
                }
                Result.failure(error)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Auth methods
    suspend fun register(request: UserRegisterRequest): Result<UserResponse> {
        return handleResponse(authApi.register(request))
    }

    suspend fun login(request: UserLoginRequest): Result<JwtLoginResponse> {
        return handleResponse(authApi.login(request))
    }

    // Trip methods
    suspend fun getTrips(): Result<List<TripResponse>> {
        return handleResponse(tripApi.getTrips())
    }

    suspend fun createTrip(request: TripCreateRequest): Result<TripCreateResponse> {
        return handleResponse(tripApi.createTrip(request))
    }

    suspend fun getTrip(id: UUID): Result<TripResponse> {
        return handleResponse(tripApi.getTrip(id))
    }

    suspend fun updateTrip(id: UUID, request: TripUpdateRequest): Result<TripResponse> {
        return handleResponse(tripApi.updateTrip(id, request))
    }

    suspend fun deleteTrip(id: UUID): Result<Unit> {
        return handleResponse(tripApi.deleteTrip(id))
    }

    // Expense methods
    suspend fun getExpenses(tripId: UUID): Result<List<ExpenseResponse>> {
        return handleResponse(expenseApi.getExpenses(tripId))
    }

    suspend fun createExpense(tripId: UUID, request: ExpenseCreateRequest): Result<ExpenseCreateResponse> {
        return handleResponse(expenseApi.createExpense(tripId, request))
    }

    suspend fun getExpense(tripId: UUID, expenseId: UUID): Result<ExpenseResponse> {
        return handleResponse(expenseApi.getExpense(tripId, expenseId))
    }

    suspend fun updateExpense(tripId: UUID, expenseId: UUID, request: ExpenseUpdateRequest): Result<ExpenseResponse> {
        return handleResponse(expenseApi.updateExpense(tripId, expenseId, request))
    }

    suspend fun deleteExpense(tripId: UUID, expenseId: UUID): Result<Unit> {
        return handleResponse(expenseApi.deleteExpense(tripId, expenseId))
    }

    // Route methods
    suspend fun getRoutePoints(tripId: UUID): Result<List<RoutePointResponse>> {
        return handleResponse(routeApi.getRoutePoints(tripId))
    }

    suspend fun createRoutePoint(tripId: UUID, request: RoutePointCreateRequest): Result<RoutePointResponse> {
        return handleResponse(routeApi.createRoutePoint(tripId, request))
    }

    suspend fun updateRoutePoint(tripId: UUID, routeId: UUID, request: RoutePointUpdateRequest): Result<RoutePointResponse> {
        return handleResponse(routeApi.updateRoutePoint(tripId, routeId, request))
    }

    suspend fun deleteRoutePoint(tripId: UUID, routeId: UUID): Result<Unit> {
        return handleResponse(routeApi.deleteRoutePoint(tripId, routeId))
    }

    // Participant methods
    suspend fun getParticipants(tripId: UUID): Result<List<ParticipantResponse>> {
        return handleResponse(participantApi.getParticipants(tripId))
    }

    suspend fun addParticipant(tripId: UUID, request: ParticipantAddRequest): Result<ParticipantResponse> {
        return handleResponse(participantApi.addParticipant(tripId, request))
    }

    suspend fun removeParticipant(tripId: UUID, userId: UUID): Result<Unit> {
        return handleResponse(participantApi.removeParticipant(tripId, userId))
    }

    // Settlement methods
    suspend fun getSettlements(tripId: UUID): Result<List<SettlementResponse>> {
        return handleResponse(settlementApi.getSettlements(tripId))
    }
}