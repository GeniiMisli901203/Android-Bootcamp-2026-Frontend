package ru.sicampus.bootcamp2026.network.models.response

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class RoutePointResponse(
    val id: UUID,
    val tripId: UUID,
    val position: Int,
    val name: String,
    val description: String?,
    val address: String?,
    val latitude: BigDecimal?,
    val longitude: BigDecimal?,
    val plannedStartDate: LocalDate?,
    val plannedEndDate: LocalDate?
)