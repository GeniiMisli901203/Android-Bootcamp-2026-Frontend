package ru.sicampus.bootcamp2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel
import ru.sicampus.bootcamp2026.ui.auth.LoginScreen
import ru.sicampus.bootcamp2026.ui.auth.RegisterScreen
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthState
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthViewModel
import ru.sicampus.bootcamp2026.ui.home.HomeScreen
import ru.sicampus.bootcamp2026.ui.profie.ProfileScreen
import ru.sicampus.bootcamp2026.ui.trips.TripsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            val loginState by authViewModel.loginState.collectAsStateWithLifecycle()

            // Обработка успешного логина
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

            // Обработка успешной регистрации
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

        composable(Screen.Home.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Trips.route) {
            TripsScreen()
        }

        composable(Screen.Profile.route) {
            val authViewModel: AuthViewModel = koinViewModel()
            ProfileScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}