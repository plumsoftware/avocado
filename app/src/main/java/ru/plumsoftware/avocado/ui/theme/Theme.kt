package ru.plumsoftware.avocado.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    // Основные цвета бренда
    primary = AvocadoGreenLight,
    onPrimary = Color.White, // Белый текст на зеленой кнопке всегда хорошо читается
    primaryContainer = FoodVeggieLight, // Используем пастель для легких контейнеров
    onPrimaryContainer = AvocadoPeelLight, // Темно-зеленый текст на пастели

    // Вторичные (Кожура)
    secondary = AvocadoPeelLight,
    onSecondary = Color.White,
    secondaryContainer = surfaceVariantLight,
    onSecondaryContainer = Color.Black,

    // Третичные (Косточка)
    tertiary = AvocadoPitLight,
    onTertiary = Color.White,
    tertiaryContainer = FoodNutLight,
    onTertiaryContainer = Color.Black,

    // Ошибки
    error = errorLight,
    onError = Color.White,

    // --- iOS FUNDAMENTALS ---
    background = backgroundLight, // Светло-серый
    onBackground = onBackgroundLight,

    surface = surfaceLight, // Белые карточки
    onSurface = onSurfaceLight,

    surfaceVariant = surfaceVariantLight, // Поля ввода, поиск
    onSurfaceVariant = SystemGrayLight, // Цвет плейсхолдеров

    outline = outlineLight,
    outlineVariant = Color(0xFFD1D1D6)
)

private val DarkColorScheme = darkColorScheme(
    // Основные цвета бренда
    primary = AvocadoGreenDark,
    onPrimary = Color.Black, // На ярком лаймовом лучше черный текст, или белый (надо тестить, оставим черный для контраста)
    primaryContainer = AvocadoPeelLight, // Темный зеленый контейнер
    onPrimaryContainer = Color.White,

    // Вторичные
    secondary = AvocadoPeelDark,
    onSecondary = Color.Black,
    secondaryContainer = surfaceVariantDark,
    onSecondaryContainer = Color.White,

    // Третичные
    tertiary = AvocadoPitDark,
    onTertiary = Color.Black,
    tertiaryContainer = FoodNutDark,
    onTertiaryContainer = Color.White,

    // Ошибки
    error = errorDark,
    onError = Color.Black,

    // --- iOS FUNDAMENTALS ---
    background = backgroundDark, // Черный
    onBackground = onBackgroundDark,

    surface = surfaceDark, // Темно-серый
    onSurface = onSurfaceDark,

    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = SystemGrayDark,

    outline = outlineDark,
    outlineVariant = Color(0xFF48484A)
)

@Composable
fun AvocadoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}