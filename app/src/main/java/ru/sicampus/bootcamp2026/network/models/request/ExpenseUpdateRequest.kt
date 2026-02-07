package ru.sicampus.bootcamp2026.network.models.request

data class ExpenseUpdateRequest(
    val amountCents: Long? = null,
    val currencyCode: String? = null,
    val description: String? = null,
    val category: String? = null,
    val splits: List<ExpenseSplitRequest>? = null
)