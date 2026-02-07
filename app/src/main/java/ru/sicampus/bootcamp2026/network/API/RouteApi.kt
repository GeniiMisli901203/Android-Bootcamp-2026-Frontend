package ru.sicampus.bootcamp2026.network.API

import retrofit2.Response
import retrofit2.http.*
import ru.sicampus.bootcamp2026.network.models.request.RoutePointCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.RoutePointUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.RoutePointResponse
import java.util.UUID

interface RouteApi {
    @GET("trips/{tripId}/routes")
    suspend fun getRoutePoints(@Path("tripId") tripId: UUID): Response<List<RoutePointResponse>>

    @POST("trips/{tripId}/routes")
    suspend fun createRoutePoint(
        @Path("tripId") tripId: UUID,
        @Body request: RoutePointCreateRequest
    ): Response<RoutePointResponse>

    @PUT("trips/{tripId}/routes/{routeId}")
    suspend fun updateRoutePoint(
        @Path("tripId") tripId: UUID,
        @Path("routeId") routeId: UUID,
        @Body request: RoutePointUpdateRequest
    ): Response<RoutePointResponse>

    @DELETE("trips/{tripId}/routes/{routeId}")
    suspend fun deleteRoutePoint(
        @Path("tripId") tripId: UUID,
        @Path("routeId") routeId: UUID
    ): Response<Unit>
}