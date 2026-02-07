package ru.sicampus.bootcamp2026.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.request.RoutePointCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.RoutePointUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.RoutePointResponse
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class RouteRepository(
    private val travoRepository: TravoRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getRoutePoints(tripId: UUID): Result<List<RoutePointResponse>> {
        return travoRepository.getRoutePoints(tripId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createRoutePoint(
        tripId: UUID,
        position: Int,
        name: String,
        description: String? = null,
        address: String? = null,
        latitude: BigDecimal? = null,
        longitude: BigDecimal? = null,
        plannedStartDate: LocalDate? = null,
        plannedEndDate: LocalDate? = null
    ): Result<RoutePointResponse> {
        val request = RoutePointCreateRequest(
            position = position,
            name = name,
            description = description,
            address = address,
            latitude = latitude,
            longitude = longitude,
            plannedStartDate = plannedStartDate,
            plannedEndDate = plannedEndDate
        )
        return travoRepository.createRoutePoint(tripId, request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateRoutePoint(
        tripId: UUID,
        routeId: UUID,
        position: Int? = null,
        name: String? = null,
        description: String? = null,
        address: String? = null,
        latitude: BigDecimal? = null,
        longitude: BigDecimal? = null,
        plannedStartDate: LocalDate? = null,
        plannedEndDate: LocalDate? = null
    ): Result<RoutePointResponse> {
        val request = RoutePointUpdateRequest(
            position = position,
            name = name,
            description = description,
            address = address,
            latitude = latitude,
            longitude = longitude,
            plannedStartDate = plannedStartDate,
            plannedEndDate = plannedEndDate
        )
        return travoRepository.updateRoutePoint(tripId, routeId, request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteRoutePoint(tripId: UUID, routeId: UUID): Result<Unit> {
        return travoRepository.deleteRoutePoint(tripId, routeId)
    }

    suspend fun reorderRoutePoints(tripId: UUID, routePoints: List<RoutePointResponse>): Result<Unit> {
        // Обновляем позиции всех точек маршрута
        val updateResults = routePoints.mapIndexed { index, routePoint ->
            updateRoutePoint(
                tripId = tripId,
                routeId = routePoint.id,
                position = index
            )
        }

        return if (updateResults.all { it.isSuccess }) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Ошибка при обновлении порядка точек маршрута"))
        }
    }
}