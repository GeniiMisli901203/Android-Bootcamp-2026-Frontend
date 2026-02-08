package ru.sicampus.bootcamp2026.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.sicampus.bootcamp2026.data.local.PreferencesManager
import ru.sicampus.bootcamp2026.data.repository.*
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthViewModel
import ru.sicampus.bootcamp2026.ui.expenses.ExpenseViewModel
import ru.sicampus.bootcamp2026.ui.participant.ParticipantViewModel
import ru.sicampus.bootcamp2026.ui.profie.ProfileViewModel
import ru.sicampus.bootcamp2026.ui.trips.TripViewModel



val appModule = module {
    // Preferences
    single { androidContext().getSharedPreferences("travo_prefs", Context.MODE_PRIVATE) }
    single { PreferencesManager(get()) }

    // Network
    single { TravoRepository(androidContext()) }

    // Repositories
    single { AuthRepository(get(), get()) }
    single { TripRepository(get()) }
    single { ExpenseRepository(get()) }
    single { ParticipantRepository(get()) }
    single { RouteRepository(get()) }
    single { SettlementRepository(get()) }
    single { UserRepository(get(), get()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { TripViewModel() }
    viewModel { ExpenseViewModel() }
    viewModel { ParticipantViewModel() }
    viewModel { ProfileViewModel() }
}