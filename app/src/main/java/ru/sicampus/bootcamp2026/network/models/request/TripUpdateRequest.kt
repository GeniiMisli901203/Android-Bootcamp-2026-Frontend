package ru.sicampus.bootcamp2026.network.models.request

data class TripUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val currency: String? = null // Было currencyCode
)