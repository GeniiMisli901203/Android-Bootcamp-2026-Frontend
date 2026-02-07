package ru.sicampus.bootcamp2026.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.network.models.request.ExpenseCreateRequest
import ru.sicampus.bootcamp2026.network.models.request.ExpenseSplitRequest
import ru.sicampus.bootcamp2026.network.models.request.ExpenseUpdateRequest
import ru.sicampus.bootcamp2026.network.models.response.ExpenseCreateResponse
import ru.sicampus.bootcamp2026.network.models.response.ExpenseResponse
import java.time.LocalDateTime
import java.util.UUID

class ExpenseRepository(
    private val travoRepository: TravoRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpenses(tripId: UUID): Result<List<ExpenseResponse>> {
        return travoRepository.getExpenses(tripId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createExpense(
        tripId: UUID,
        paidById: UUID,
        amountCents: Long,
        currencyCode: String,
        description: String? = null,
        category: String? = null,
        paidAt: LocalDateTime? = null,
        splits: List<Pair<UUID, Long>> // userId to amountCents
    ): Result<ExpenseCreateResponse> {
        val splitRequests = splits.map { (userId, amount) ->
            ExpenseSplitRequest(userId = userId, amountCents = amount)
        }

        val request = ExpenseCreateRequest(
            paidById = paidById,
            amountCents = amountCents,
            currencyCode = currencyCode,
            description = description,
            category = category,
            splits = splitRequests
        )

        return travoRepository.createExpense(tripId, request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpense(tripId: UUID, expenseId: UUID): Result<ExpenseResponse> {
        return travoRepository.getExpense(tripId, expenseId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateExpense(
        tripId: UUID,
        expenseId: UUID,
        amountCents: Long? = null,
        currencyCode: String? = null,
        description: String? = null,
        category: String? = null,
        splits: List<Pair<UUID, Long>>? = null
    ): Result<ExpenseResponse> {
        val splitRequests = splits?.map { (userId, amount) ->
            ExpenseSplitRequest(userId = userId, amountCents = amount)
        }

        val request = ExpenseUpdateRequest(
            amountCents = amountCents,
            currencyCode = currencyCode,
            description = description,
            category = category,
            splits = splitRequests
        )

        return travoRepository.updateExpense(tripId, expenseId, request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteExpense(tripId: UUID, expenseId: UUID): Result<Unit> {
        return travoRepository.deleteExpense(tripId, expenseId)
    }

    fun convertToCents(amount: Double): Long = (amount * 100).toLong()

    fun convertFromCents(amountCents: Long): Double = amountCents.toDouble() / 100

    fun formatCurrency(amountCents: Long, currencyCode: String): String {
        val amount = convertFromCents(amountCents)
        return when (currencyCode) {
            "RUB" -> "%.2f ₽".format(amount)
            "USD" -> "$%.2f".format(amount)
            "EUR" -> "€%.2f".format(amount)
            else -> "%.2f $currencyCode".format(amount)
        }
    }
}