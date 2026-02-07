package ru.sicampus.bootcamp2026.network.models.response

import java.util.UUID


data class TripResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val startDate: String?, // String вместо LocalDate
    val endDate: String?,
    val currencyCode: String, // Важно: имя должно совпадать с сервером!
    val createdById: UUID,
    val createdAt: String,
)