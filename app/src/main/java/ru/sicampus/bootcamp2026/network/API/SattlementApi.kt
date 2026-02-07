package ru.sicampus.bootcamp2026.network.API

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sicampus.bootcamp2026.network.models.response.SettlementResponse
import java.util.UUID

interface SettlementApi {
    @GET("trips/{tripId}/settlements")
    suspend fun getSettlements(@Path("tripId") tripId: UUID): Response<List<SettlementResponse>>
}