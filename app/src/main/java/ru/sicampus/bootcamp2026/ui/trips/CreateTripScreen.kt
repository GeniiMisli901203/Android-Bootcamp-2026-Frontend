package ru.sicampus.bootcamp2026.ui.trips

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import ru.sicampus.bootcamp2026.ui.navigation.Screen
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    navController: NavController,
    viewModel: TripViewModel
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("RUB") }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val scope = rememberCoroutineScope()

    val currencies = listOf("RUB", "USD", "EUR", "KZT")
    var showCurrencyMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Новая поездка") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (name.isNotBlank()) {
                                scope.launch {
                                    viewModel.createTrip(
                                        name = name,
                                        description = description.takeIf { it.isNotBlank() },
                                        startDate = null,
                                        endDate = null,
                                        currency = currency
                                    ) { tripId ->
                                        navController.navigate(Screen.TripDetail.createRoute(tripId.toString())) {
                                            popUpTo(Screen.Home.route) { inclusive = false }
                                        }
                                    }
                                }
                            }
                        },
                        enabled = name.isNotBlank() && !isLoading
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Создать")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Название поездки
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Название поездки *") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Check, contentDescription = "Название")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = name.isBlank()
                    )

                    // Описание
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Описание") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Info, contentDescription = "Описание")
                        },
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    )

                    // Валюта
                    Box {
                        OutlinedTextField(
                            value = currency,
                            onValueChange = { },
                            label = { Text("Валюта") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(Icons.Default.Menu, contentDescription = "Валюта")
                            },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { showCurrencyMenu = true }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Выбрать валюту")
                                }
                            }
                        )

                        DropdownMenu(
                            expanded = showCurrencyMenu,
                            onDismissRequest = { showCurrencyMenu = false }
                        ) {
                            currencies.forEach { selectedCurrency ->
                                DropdownMenuItem(
                                    text = { Text(selectedCurrency) },
                                    onClick = {
                                        currency = selectedCurrency
                                        showCurrencyMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}