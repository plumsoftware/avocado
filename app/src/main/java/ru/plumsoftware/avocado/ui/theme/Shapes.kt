package ru.plumsoftware.avocado.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// В iOS скругления играют ключевую роль.
// Мы используем более крупные значения, чем в стандартном Android,
// чтобы интерфейс выглядел "мягким" и "тактильным", как само авокадо.

val Shapes = Shapes(
    // extraSmall: Для совсем мелких элементов (тегов внутри карточек)
    // В iOS минимальное скругление редко бывает меньше 6-8 dp
    extraSmall = RoundedCornerShape(6.dp),

    // small: Для полей ввода (TextFields), кнопок поиска (Search Bars)
    // Стандарт iOS для полей ввода — около 10-12 dp
    small = RoundedCornerShape(10.dp),

    // medium: СТАНДАРТ для карточек продуктов и секций меню
    // Это основной шейп для твоих списков "Овощи", "Фрукты"
    medium = RoundedCornerShape(16.dp),

    // large: Для диалоговых окон (Alerts) и крупных виджетов
    // Apple использует ~14-20 dp для алертов
    large = RoundedCornerShape(20.dp),

    // extraLarge: Для нижних шторок (Bottom Sheets / Modal Sheets)
    // Верхние углы шторки должны быть сильно скруглены
    extraLarge = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
)