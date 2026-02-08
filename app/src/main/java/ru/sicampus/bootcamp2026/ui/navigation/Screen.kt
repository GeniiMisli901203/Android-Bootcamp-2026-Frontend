package ru.sicampus.bootcamp2026.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Trips : Screen("trips")
    object CreateTrip : Screen("create_trip")
    object TripDetail : Screen("trip_detail/{tripId}") {
        fun createRoute(tripId: String) = "trip_detail/$tripId"
    }
    object Expenses : Screen("expenses/{tripId}") {
        fun createRoute(tripId: String) = "expenses/$tripId"
    }
    object CreateExpense : Screen("create_expense/{tripId}") {
        fun createRoute(tripId: String) = "create_expense/$tripId"
    }
    object Participants : Screen("participants/{tripId}") {
        fun createRoute(tripId: String) = "participants/$tripId"
    }
    object Settlements : Screen("settlements/{tripId}") {
        fun createRoute(tripId: String) = "settlements/$tripId"
    }
    object RoutePoints : Screen("route_points/{tripId}") {
        fun createRoute(tripId: String) = "route_points/$tripId"
    }
}