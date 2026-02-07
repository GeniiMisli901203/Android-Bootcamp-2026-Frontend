// ui/theme/Theme.kt
package com.travo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import ru.sicampus.bootcamp2026.ui.theme.Pink40
import ru.sicampus.bootcamp2026.ui.theme.Pink80
import ru.sicampus.bootcamp2026.ui.theme.Purple40
import ru.sicampus.bootcamp2026.ui.theme.Purple80
import ru.sicampus.bootcamp2026.ui.theme.PurpleGrey40
import ru.sicampus.bootcamp2026.ui.theme.PurpleGrey80
import ru.sicampus.bootcamp2026.ui.theme.Typography

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun TravoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}