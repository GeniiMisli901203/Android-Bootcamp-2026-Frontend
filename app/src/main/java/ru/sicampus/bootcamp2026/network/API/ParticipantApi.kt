package ru.sicampus.bootcamp2026.network.API

import retrofit2.Response
import retrofit2.http.*
import ru.sicampus.bootcamp2026.network.models.request.ParticipantAddRequest
import ru.sicampus.bootcamp2026.network.models.response.ParticipantResponse
import java.util.UUID

interface ParticipantApi {
    @GET("trips/{tripId}/participants")
    suspend fun getParticipants(@Path("tripId") tripId: UUID): Response<List<ParticipantResponse>>

    @POST("trips/{tripId}/participants")
    suspend fun addParticipant(
        @Path("tripId") tripId: UUID,
        @Body request: ParticipantAddRequest
    ): Response<ParticipantResponse>

    @DELETE("trips/{tripId}/participants/{userId}")
    suspend fun removeParticipant(
        @Path("tripId") tripId: UUID,
        @Path("userId") userId: UUID
    ): Response<Unit>
}