package ru.sicampus.bootcamp2026.network.models.request

import java.math.BigDecimal
import java.time.LocalDate

data class RoutePointCreateRequest(
    val position: Int,
    val name: String,
    val description: String? = null,
    val address: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,
    val plannedStartDate: LocalDate? = null,
    val plannedEndDate: LocalDate? = null
)