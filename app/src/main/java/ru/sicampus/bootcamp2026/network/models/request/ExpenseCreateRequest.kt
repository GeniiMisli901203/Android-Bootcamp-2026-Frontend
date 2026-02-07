package ru.sicampus.bootcamp2026.network.models.request

import java.util.UUID

data class ExpenseCreateRequest(
    val paidById: UUID,
    val amountCents: Long,
    val currencyCode: String,
    val description: String? = null,
    val category: String? = null,
    val splits: List<ExpenseSplitRequest>
)

data class ExpenseSplitRequest(
    val userId: UUID,
    val amountCents: Long
)