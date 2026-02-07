package ru.sicampus.bootcamp2026.data.repository

import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.response.SettlementResponse
import java.util.UUID

class SettlementRepository(
    private val travoRepository: TravoRepository
) {

    suspend fun getSettlements(tripId: UUID): Result<List<SettlementResponse>> {
        return travoRepository.getSettlements(tripId)
    }

    suspend fun getMySettlements(tripId: UUID, myUserId: UUID): Result<List<SettlementResponse>> {
        return getSettlements(tripId).map { settlements ->
            settlements.filter {
                it.fromUserId == myUserId || it.toUserId == myUserId
            }
        }
    }

    suspend fun getTotalOwedToMe(tripId: UUID, myUserId: UUID): Result<Long> {
        return getSettlements(tripId).map { settlements ->
            settlements
                .filter { it.toUserId == myUserId }
                .sumOf { it.amountCents }
        }
    }

    suspend fun getTotalIOwe(tripId: UUID, myUserId: UUID): Result<Long> {
        return getSettlements(tripId).map { settlements ->
            settlements
                .filter { it.fromUserId == myUserId }
                .sumOf { it.amountCents }
        }
    }

    suspend fun getNetBalance(tripId: UUID, myUserId: UUID): Result<Long> {
        return getSettlements(tripId).map { settlements ->
            val owedToMe = settlements
                .filter { it.toUserId == myUserId }
                .sumOf { it.amountCents }

            val iOwe = settlements
                .filter { it.fromUserId == myUserId }
                .sumOf { it.amountCents }

            owedToMe - iOwe
        }
    }

    fun formatSettlementText(settlement: SettlementResponse, myUserId: UUID): String {
        return if (settlement.fromUserId == myUserId) {
            "Вы должны ${settlement.toUserDisplayName}: ${formatAmount(settlement.amountCents, settlement.currencyCode)}"
        } else {
            "${settlement.fromUserDisplayName} должен вам: ${formatAmount(settlement.amountCents, settlement.currencyCode)}"
        }
    }

    private fun formatAmount(amountCents: Long, currencyCode: String): String {
        val amount = amountCents.toDouble() / 100
        return when (currencyCode) {
            "RUB" -> "%.2f ₽".format(amount)
            "USD" -> "$%.2f".format(amount)
            "EUR" -> "€%.2f".format(amount)
            else -> "%.2f $currencyCode".format(amount)
        }
    }
}