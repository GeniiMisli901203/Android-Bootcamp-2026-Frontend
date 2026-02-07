package ru.sicampus.bootcamp2026.network.API

import retrofit2.Response
import retrofit2.http.*
import ru.sicampus.bootcamp2026.network.models.request.ExpenseCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.ExpenseUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.ExpenseCreateResponse
import ru.sicampus.bootcamp2026.network.models.response.ExpenseResponse
import java.util.UUID

interface ExpenseApi {
    @GET("trips/{tripId}/expenses")
    suspend fun getExpenses(@Path("tripId") tripId: UUID): Response<List<ExpenseResponse>>

    @POST("trips/{tripId}/expenses")
    suspend fun createExpense(
        @Path("tripId") tripId: UUID,
        @Body request: ExpenseCreateRequest
    ): Response<ExpenseCreateResponse>

    @GET("trips/{tripId}/expenses/{expenseId}")
    suspend fun getExpense(
        @Path("tripId") tripId: UUID,
        @Path("expenseId") expenseId: UUID
    ): Response<ExpenseResponse>

    @PUT("trips/{tripId}/expenses/{expenseId}")
    suspend fun updateExpense(
        @Path("tripId") tripId: UUID,
        @Path("expenseId") expenseId: UUID,
        @Body request: ExpenseUpdateRequest
    ): Response<ExpenseResponse>

    @DELETE("trips/{tripId}/expenses/{expenseId}")
    suspend fun deleteExpense(
        @Path("tripId") tripId: UUID,
        @Path("expenseId") expenseId: UUID
    ): Response<Unit>
}