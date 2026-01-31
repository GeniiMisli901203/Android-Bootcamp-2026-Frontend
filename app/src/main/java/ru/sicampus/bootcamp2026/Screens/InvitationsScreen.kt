package ru.sicampus.bootcamp2026.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose.ui.screens.Meeting
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationsScreen() {

    val invitations = remember {
        listOf(
            Meeting(
                id = 1,
                title = "Еженедельный стендап",
                description = "Обсуждение задач на неделю",
                date = Date(),
                participants = listOf("Иван", "Мария", "Алексей")
            ),
            Meeting(
                id = 2,
                title = "Планирование проекта",
                description = "Определение сроков и ресурсов",
                date = Date(System.currentTimeMillis() + 86400000), // Завтра
                participants = listOf("Ольга", "Дмитрий", "Екатерина")
            ),
            Meeting(
                id = 3,
                title = "Ретроспектива",
                description = "Анализ проделанной работы",
                date = Date(System.currentTimeMillis() - 86400000), // Вчера
                participants = listOf("Сергей", "Анна", "Павел")
            ),
            Meeting(
                id = 4,
                title = "Встреча с клиентом",
                description = "Обсуждение требований",
                date = Date(System.currentTimeMillis() + 172800000), // Послезавтра
                participants = listOf("Клиент", "Менеджер", "Разработчик")
            ),
            Meeting(
                id = 5,
                title = "Обучение новым технологиям",
                description = "Введение в Kotlin Multiplatform",
                date = Date(System.currentTimeMillis() + 259200000), // Через 3 дня
                participants = listOf("Все разработчики")
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Приглашения",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->
        if (invitations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Нет приглашений",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(invitations) { invitation ->
                    InvitationCard(invitations = invitation)
                }
            }
        }
    }



}


@Composable
fun InvitationCard(invitations: Meeting) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Заголовок и дата
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = invitations.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = dateFormat.format(invitations.date),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy()
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { /* Принять приглашение */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Принять",
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { /* Отклонить приглашение */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Отклонить",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
data class Invitation(
    val id: Int,
    val title: String,
    val time: Time,
    val date: Date,
)
