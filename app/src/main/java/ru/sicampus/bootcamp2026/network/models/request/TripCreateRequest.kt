package ru.sicampus.bootcamp2026.network.models.request
data class TripCreateRequest(
    val name: String,
    val description: String? = null,
    val startDate: String? = null, // String для совместимости
    val endDate: String? = null,
    val currency: String = "RUB" // Было currencyCode
)