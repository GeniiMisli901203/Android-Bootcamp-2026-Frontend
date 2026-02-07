package ru.sicampus.bootcamp2026.network.models.response

import java.util.UUID

data class SettlementResponse(
    val fromUserId: UUID,
    val fromUserDisplayName: String,
    val toUserId: UUID,
    val toUserDisplayName: String,
    val amountCents: Long,
    val currencyCode: String
)