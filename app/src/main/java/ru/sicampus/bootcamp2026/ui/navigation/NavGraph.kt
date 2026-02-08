package ru.sicampus.bootcamp2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import ru.sicampus.bootcamp2026.ui.auth.LoginScreen
import ru.sicampus.bootcamp2026.ui.auth.RegisterScreen
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthState
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthViewModel
import ru.sicampus.bootcamp2026.ui.expenses.ExpenseViewModel
import ru.sicampus.bootcamp2026.ui.expenses.ExpensesScreen
import ru.sicampus.bootcamp2026.ui.home.HomeScreen
import ru.sicampus.bootcamp2026.ui.profie.ProfileViewModel
import ru.sicampus.bootcamp2026.ui.profile.ProfileScreen
import ru.sicampus.bootcamp2026.ui.trips.CreateTripScreen
import ru.sicampus.bootcamp2026.ui.trips.TripDetailScreen
import ru.sicampus.bootcamp2026.ui.trips.TripViewModel
import ru.sicampus.bootcamp2026.ui.trips.TripsScreen
import java.util.UUID

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth screens
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            val loginState by authViewModel.loginState.collectAsStateWithLifecycle()

            LaunchedEffect(loginState) {
                if (loginState is AuthState.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    authViewModel.resetLoginState()
                }
            }

            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                loginState = loginState
            )
        }

        composable(Screen.Register.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            val registerState by authViewModel.registerState.collectAsStateWithLifecycle()

            LaunchedEffect(registerState) {
                if (registerState is AuthState.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                    authViewModel.resetRegisterState()
                }
            }

            RegisterScreen(
                onRegisterClick = { email, displayName, password ->
                    authViewModel.register(email, displayName, password)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                registerState = registerState
            )
        }

        // Main screens
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }

        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = koinViewModel()
            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel
            )
        }

        composable(Screen.Trips.route) {
            TripsScreen(
                navController = navController
            )
        }

        // Trip screens
        composable(Screen.CreateTrip.route) {
            val tripViewModel: TripViewModel = koinViewModel()
            CreateTripScreen(
                navController = navController,
                viewModel = tripViewModel
            )
        }

        composable(
            route = Screen.TripDetail.route,
            arguments = listOf(navArgument("tripId") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
            val tripViewModel: TripViewModel = koinViewModel()

            LaunchedEffect(tripId) {
                tripViewModel.loadTrip(UUID.fromString(tripId))
            }

            TripDetailScreen(
                navController = navController,
                tripId = tripId,
                viewModel = tripViewModel
            )
        }

        // Expense screens
        composable(
            route = Screen.Expenses.route,
            arguments = listOf(navArgument("tripId") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
            val expenseViewModel: ExpenseViewModel = koinViewModel()

            LaunchedEffect(tripId) {
                expenseViewModel.loadExpenses(UUID.fromString(tripId))
            }

            ExpensesScreen(
                navController = navController,
                tripId = tripId,
                viewModel = expenseViewModel
            )
        }
    }
}