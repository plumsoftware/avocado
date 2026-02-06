package ru.plumsoftware.avocado.ui.theme

// Set of Material typography styles to start with
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.plumsoftware.avocado.R

// 1. Создаем семейство шрифтов
// Мы определяем, какой файл за какую толщину отвечает
val AppFont = FontFamily(
    Font(R.font.inter_24pt_regular, FontWeight.Normal),
    Font(R.font.inter_24pt_medium, FontWeight.Medium),
    Font(R.font.inter_24pt_semibold, FontWeight.SemiBold),
    Font(R.font.inter_24pt_bold, FontWeight.Bold)
)

// 2. Настраиваем Типографику
// Метрики (sp) и letterSpacing подогнаны под iOS Human Interface Guidelines

val Typography = Typography(
    // --- ЗАГОЛОВКИ (Headings) ---

    // iOS: Large Title (34pt) - "Сегодня", "Настройки"
    displayLarge = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.37.sp
    ),

    // iOS: Title 1 (28pt) - Крупные акценты
    displayMedium = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.36.sp
    ),

    // iOS: Title 2 (22pt) - Заголовки разделов
    titleLarge = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.35.sp
    ),

    // iOS: Title 3 (20pt) - Названия продуктов в списке
    titleMedium = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.38.sp
    ),

    // iOS: Headline (17pt Bold/SemiBold) - Акцентный текст того же размера, что и основной
    titleSmall = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.41).sp
    ),

    // --- ОСНОВНОЙ ТЕКСТ (Body) ---

    // iOS: Body (17pt) - САМЫЙ ВАЖНЫЙ СТИЛЬ
    // Android по умолчанию использует 16sp, iOS использует 17sp.
    // Это ключевое отличие, которое дает ощущение "iOS".
    bodyLarge = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.41).sp
    ),

    // iOS: Callout (16pt) - Врезки текста
    bodyMedium = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        letterSpacing = (-0.32).sp
    ),

    // iOS: Subhead (15pt) - Дополнительное описание
    bodySmall = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.24).sp
    ),

    // --- ПОДПИСИ (Captions/Labels) ---

    // iOS: Footnote (13pt) - Подписи к полям, мелкие детали
    labelLarge = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Normal, // В iOS Footnote обычно Regular
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = (-0.08).sp
    ),

    // iOS: Caption 1 (12pt) - КБЖУ, теги
    labelMedium = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),

    // iOS: Caption 2 (11pt) - Самый мелкий технический текст
    labelSmall = TextStyle(
        fontFamily = AppFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.06.sp
    )
)