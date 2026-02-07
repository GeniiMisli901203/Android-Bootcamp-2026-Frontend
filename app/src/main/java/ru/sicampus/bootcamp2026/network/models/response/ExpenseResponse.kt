package ru.sicampus.bootcamp2026.network.models.response

import java.time.Instant
import java.util.UUID

data class ExpenseResponse(
    val id: UUID,
    val tripId: UUID,
    val paidById: UUID,
    val paidByDisplayName: String,
    val amountCents: Long,
    val currencyCode: String,
    val description: String?,
    val category: String?,
    val createdAt: Instant,
    val splits: List<ExpenseSplitResponse>
)

data class ExpenseSplitResponse(
    val userId: UUID,
    val displayName: String,
    val amountCents: Long
)