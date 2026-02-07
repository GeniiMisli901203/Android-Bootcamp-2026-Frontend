package ru.sicampus.bootcamp2026.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import ru.sicampus.bootcamp2026.R

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int,
    val selectedIcon: Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Главная",
        route = Screen.Home.route,
        icon = R.drawable.ic_home_outline,
        selectedIcon = R.drawable.ic_home_filled
    ),
    BottomNavItem(
        name = "Поездки",
        route = Screen.Trips.route,
        icon = R.drawable.ic_trip_outline,
        selectedIcon = R.drawable.ic_trip_filled
    ),
    BottomNavItem(
        name = "Профиль",
        route = Screen.Profile.route,
        icon = R.drawable.ic_profile_outline,
        selectedIcon = R.drawable.ic_profile_filled
    )
)