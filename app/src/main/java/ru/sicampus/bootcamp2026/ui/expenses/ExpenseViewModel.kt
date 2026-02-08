package ru.sicampus.bootcamp2026.ui.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sicampus.bootcamp2026.data.repository.ExpenseRepository
import ru.sicampus.bootcamp2026.network.models.response.ExpenseResponse
import java.time.LocalDateTime
import java.util.UUID



class ExpenseViewModel : ViewModel(), KoinComponent {
    private val expenseRepository: ExpenseRepository by inject()

    private val _expensesState = MutableStateFlow<List<ExpenseResponse>>(emptyList())
    val expensesState: StateFlow<List<ExpenseResponse>> = _expensesState.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun loadExpenses(tripId: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            expenseRepository.getExpenses(tripId).fold(
                onSuccess = { expenses ->
                    _expensesState.value = expenses
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка загрузки расходов: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun createExpense(
        tripId: UUID,
        paidById: UUID,
        amountCents: Long,
        currencyCode: String,
        description: String? = null,
        category: String? = null,
        paidAt: LocalDateTime? = null,
        splits: List<Pair<UUID, Long>>,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            expenseRepository.createExpense(
                tripId = tripId,
                paidById = paidById,
                amountCents = amountCents,
                currencyCode = currencyCode,
                description = description,
                category = category,
                paidAt = paidAt,
                splits = splits
            ).fold(
                onSuccess = {
                    loadExpenses(tripId)
                    onSuccess()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка создания расхода: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun deleteExpense(tripId: UUID, expenseId: UUID, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            expenseRepository.deleteExpense(tripId, expenseId).fold(
                onSuccess = {
                    loadExpenses(tripId)
                    onSuccess()
                },
                onFailure = { error ->
                    _errorMessage.value = "Ошибка удаления расхода: ${error.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun calculateTotalExpenses(): Long {
        return _expensesState.value.sumOf { it.amountCents }
    }

    fun formatCurrency(amountCents: Long, currencyCode: String): String {
        return expenseRepository.formatCurrency(amountCents, currencyCode)
    }

    fun clearError() {
        _errorMessage.value = null
    }
}