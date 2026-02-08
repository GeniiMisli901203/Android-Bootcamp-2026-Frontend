package ru.sicampus.bootcamp2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.travo.ui.theme.TravoTheme
import org.koin.androidx.compose.koinViewModel
import ru.sicampus.bootcamp2026.ui.auth.viewmodel.AuthViewModel
import ru.sicampus.bootcamp2026.ui.navigation.NavGraph
import ru.sicampus.bootcamp2026.ui.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TravoApp()
                }
            }
        }
    }
}

@Composable
fun TravoApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()

    // Проверяем авторизацию при запуске
    val startDestination = if (authViewModel.isUserLoggedIn()) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }

    NavGraph(
        navController = navController,
        startDestination = startDestination
    )
}

@Preview(showBackground = true)
@Composable
fun TravoAppPreview() {
    TravoTheme {
        TravoApp()
    }
}