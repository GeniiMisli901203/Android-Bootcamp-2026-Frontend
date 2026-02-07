package ru.sicampus.bootcamp2026.network.models.request

import java.math.BigDecimal
import java.time.LocalDate

data class RoutePointUpdateRequest(
    val position: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val address: String? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,
    val plannedStartDate: LocalDate? = null,
    val plannedEndDate: LocalDate? = null
)