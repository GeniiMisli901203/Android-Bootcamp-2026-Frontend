package ru.sicampus.bootcamp2026.network.API

import ru.sicampus.bootcamp2026.network.models.request.TripCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.TripUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.TripCreateResponse
import ru.sicampus.bootcamp2026.network.models.response.TripResponse
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface TripApi {
    @GET("trips")
    suspend fun getTrips(): Response<List<TripResponse>>

    @POST("trips")
    suspend fun createTrip(@Body request: TripCreateRequest): Response<TripCreateResponse>

    @GET("trips/{id}")
    suspend fun getTrip(@Path("id") id: UUID): Response<TripResponse>

    @PUT("trips/{id}")
    suspend fun updateTrip(
        @Path("id") id: UUID,
        @Body request: TripUpdateRequest
    ): Response<TripResponse>

    @DELETE("trips/{id}")
    suspend fun deleteTrip(@Path("id") id: UUID): Response<Unit>
}