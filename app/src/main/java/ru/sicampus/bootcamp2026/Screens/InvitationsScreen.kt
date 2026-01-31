package ru.sicampus.bootcamp2026.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

data class Invitation(
    val id: Int,
    val title: String,
    val description: String = "",
    val date: Date,
    val startTime: String,  // Например "10:00"
    val endTime: String,    // Например "11:30"
    val organizer: String = "",
    val participants: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationsScreen() {
    val invitations = remember {
        listOf(
            Invitation(
                id = 1,
                title = "Еженедельный стендап",
                description = "Обсуждение задач на неделю",
                date = Date(),
                startTime = "10:00",
                endTime = "11:00",
                organizer = "Иван Иванов",
                participants = listOf("Мария", "Алексей", "Ольга")
            ),
            Invitation(
                id = 2,
                title = "Планирование проекта",
                description = "Определение сроков и ресурсов",
                date = Date(System.currentTimeMillis() + 86400000),
                startTime = "14:00",
                endTime = "15:30",
                organizer = "Ольга Петрова",
                participants = listOf("Дмитрий", "Екатерина", "Сергей")
            ),
            Invitation(
                id = 3,
                title = "Ретроспектива",
                description = "Анализ проделанной работы за спринт",
                date = Date(System.currentTimeMillis() - 86400000),
                startTime = "16:00",
                endTime = "17:30",
                organizer = "Алексей Сидоров",
                participants = listOf("Анна", "Павел", "Михаил")
            ),
            Invitation(
                id = 4,
                title = "Встреча с клиентом",
                description = "Обсуждение требований к новому функционалу",
                date = Date(System.currentTimeMillis() + 172800000),
                startTime = "11:00",
                endTime = "12:30",
                organizer = "Менеджер проекта",
                participants = listOf("Клиент", "Разработчик", "Дизайнер")
            ),
            Invitation(
                id = 5,
                title = "Обучение новым технологиям",
                description = "Введение в Kotlin Multiplatform",
                date = Date(System.currentTimeMillis() + 259200000),
                startTime = "15:00",
                endTime = "17:00",
                organizer = "Техлид",
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
                    Text(
                        text = "Нет новых приглашений",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
                    InvitationCard(invitation = invitation)
                }
            }
        }
    }
}

@Composable
fun InvitationCard(invitation: Invitation) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Заголовок и время
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = invitation.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${dateFormat.format(invitation.date)} • ${invitation.startTime}-${invitation.endTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Text(
                        text = invitation.organizer,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Описание
            if (invitation.description.isNotEmpty()) {
                Text(
                    text = invitation.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 2
                )
            }

            // Участники
            if (invitation.participants.isNotEmpty()) {
                Text(
                    text = "Участники: ${invitation.participants.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            // Кнопки действий
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

// Альтернативная версия InvitationCard с другой структурой
@Composable
fun CompactInvitationCard(invitation: Invitation) {
    val dateFormat = remember { SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = invitation.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1
                )

                Text(
                    text = "${dateFormat.format(invitation.date)} • ${invitation.startTime}-${invitation.endTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                if (invitation.organizer.isNotEmpty()) {
                    Text(
                        text = "от ${invitation.organizer}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Кнопки в ряд
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Принять */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.size(width = 80.dp, height = 36.dp)
                ) {
                    Text("✓", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }

                Button(
                    onClick = { /* Отклонить */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.size(width = 80.dp, height = 36.dp)
                ) {
                    Text("✗", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
            }
        }
    }
}