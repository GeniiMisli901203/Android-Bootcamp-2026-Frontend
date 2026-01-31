package ru.sicampus.bootcamp2026

import AddMeeting
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.AndroidBootcamp2026FrontendTheme
import com.example.compose.ui.screens.MeetingsScreen
import ru.sicampus.bootcamp2026.screens.InvitationsScreen
import ru.sicampus.bootcamp2026.screens.LoginScreen
import ru.sicampus.bootcamp2026.screens.ProfileScreen
import ru.sicampus.bootcamp2026.screens.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBootcamp2026FrontendTheme {
                App()
            }
        }
    }
}

sealed class Screens (val route: String, val title: String, val icon: Int) {
    object Meeting : Screens ("meetings", "Встречи", R.drawable.ic_home)
    object Invitation : Screens ("Invitation", "Приглашение", R.drawable.ic_calendar)
    object Profile : Screens ("Profile", "Профиль", R.drawable.ic_profile)
}

@Composable
fun App() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in setOf(
        Screens.Meeting.route,
        Screens.Profile.route,
        Screens.Invitation.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "login_screen",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("login_screen") {
                LoginScreen(
                    onMeetingClick = {
                        navController.navigate(Screens.Meeting.route) {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate("register_screen")
                    }
                )
            }
            composable("register_screen") {
                RegisterScreen(
                    onMeetingsScreen = {
                        navController.navigate(Screens.Meeting.route) {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screens.Meeting.route) {
                MeetingsScreen(
                    onNavigateToAddMeeting = {
                        navController.navigate("add_meeting")
                    }
                )
            }
            composable(Screens.Profile.route) {
                ProfileScreen()
            }
            composable(Screens.Invitation.route) {
                InvitationsScreen()
            }
            composable("add_meeting") {
                AddMeeting(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val screens = listOf(
        Screens.Meeting,
        Screens.Invitation,
        Screens.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        screens.forEach { screens ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = screens.icon),
                        contentDescription = screens.title
                    )
                },
                label = {Text(text = screens.title)},
                selected = currentRoute == screens.route,
                onClick = {
                    if (currentRoute != screens.route) {
                        navController.navigate(screens.route) {
                            popUpTo (navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
