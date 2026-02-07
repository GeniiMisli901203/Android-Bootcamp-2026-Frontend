package ru.sicampus.bootcamp2026.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Trips : Screen("trips")
    object Profile : Screen("profile")
}