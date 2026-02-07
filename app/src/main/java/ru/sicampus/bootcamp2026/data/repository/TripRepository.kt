package ru.sicampus.bootcamp2026.data.repository

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.request.TripCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.TripUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.TripCreateResponse
import ru.sicampus.bootcamp2026.network.models.response.TripResponse
import java.util.UUID

class TripRepository(
    private val travoRepository: TravoRepository
) {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    suspend fun getTrips(): Result<List<TripResponse>> {
        return travoRepository.getTrips()
    }

    suspend fun createTrip(
        name: String,
        description: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        currency: String = "RUB"
    ): Result<TripCreateResponse> {
        val request = TripCreateRequest(
            name = name,
            description = description,
            startDate = startDate?.format(dateFormatter),
            endDate = endDate?.format(dateFormatter),
            currency = currency
        )
        return travoRepository.createTrip(request)
    }

    suspend fun getTrip(id: UUID): Result<TripResponse> {
        return travoRepository.getTrip(id)
    }

    suspend fun updateTrip(
        id: UUID,
        name: String? = null,
        description: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        currency: String? = null
    ): Result<TripResponse> {
        val request = TripUpdateRequest(
            name = name,
            description = description,
            startDate = startDate?.format(dateFormatter),
            endDate = endDate?.format(dateFormatter),
            currency = currency
        )
        return travoRepository.updateTrip(id, request)
    }

    suspend fun deleteTrip(id: UUID): Result<Unit> {
        return travoRepository.deleteTrip(id)
    }

    suspend fun getTripParticipants(tripId: UUID): Result<List<ru.sicampus.bootcamp2026.network.models.response.ParticipantResponse>> {
        return travoRepository.getParticipants(tripId)
    }

    // Вспомогательные методы
    fun parseLocalDate(dateString: String?): LocalDate? {
        return dateString?.let {
            try {
                LocalDate.parse(it, dateFormatter)
            } catch (e: Exception) {
                null
            }
        }
    }
}