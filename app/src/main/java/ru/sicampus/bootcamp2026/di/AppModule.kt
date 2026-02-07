package ru.sicampus.bootcamp2026.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.sicampus.bootcamp2026.data.local.PreferencesManager
import ru.sicampus.bootcamp2026.data.repository.AuthRepository
import ru.sicampus.bootcamp2026.network.TravoRepository
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthViewModel

val appModule = module {
    single { androidContext().getSharedPreferences("travo_prefs", Context.MODE_PRIVATE) }
    single { PreferencesManager(get()) }
    single { TravoRepository(androidContext()) }
    single { AuthRepository(get(), get()) }

    viewModel { AuthViewModel(get()) }
}