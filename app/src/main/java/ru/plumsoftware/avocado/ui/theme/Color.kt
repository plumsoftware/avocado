package ru.plumsoftware.avocado.ui.theme

import androidx.compose.ui.graphics.Color

// --- AVOCADO BRAND COLORS ---

// 1. Primary (Мякоть) - Основной Action цвет
// В светлой теме: насыщенный, аппетитный зеленый
val AvocadoGreenLight = Color(0xFF6B9E26)
// В темной теме: чуть светлее и "неоновее", чтобы читалось на черном
val AvocadoGreenDark = Color(0xFF8CD84A)

// 2. Secondary (Кожура) - Для акцентов, заголовков или "тяжелых" элементов
val AvocadoPeelLight = Color(0xFF2E4022)
// В темной теме кожура становится пастельно-фисташковой, чтобы не проваливаться в черный
val AvocadoPeelDark = Color(0xFFB5C9A8)

// 3. Tertiary (Косточка) - Для теплых акцентов (например, избранное или выпечка)
val AvocadoPitLight = Color(0xFF795548) // Классический коричневый
val AvocadoPitDark = Color(0xFFA1887F)  // Мягкий какао

// --- IOS SYSTEM COLORS (Base) ---
// Эти цвета создают то самое ощущение "нативного iOS"

// Backgrounds
val backgroundLight = Color(0xFFF2F2F7) // System Gray 6 (Фон экранов)
val backgroundDark = Color(0xFF000000)  // Pure Black (OLED)

// Surfaces (Карточки)
val surfaceLight = Color(0xFFFFFFFF)
val surfaceDark = Color(0xFF1C1C1E) // System Gray 6 Dark

// Secondary Surfaces (SearchBar, подложки)
val surfaceVariantLight = Color(0xFFE5E5EA) // System Gray 5
val surfaceVariantDark = Color(0xFF2C2C2E)

// Text Colors
val onBackgroundLight = Color.Black
val onBackgroundDark = Color.White
val onSurfaceLight = Color.Black
val onSurfaceDark = Color.White

// Grays (Для подписей)
val SystemGrayLight = Color(0xFF8E8E93)
val SystemGrayDark = Color(0xFF8E8E93) // Apple использует один серый для обоих тем часто

// Errors
val errorLight = Color(0xFFFF3B30) // Apple Red
val errorDark = Color(0xFFFF453A)

// Outlines / Dividers
val outlineLight = Color(0xFFC6C6C8) // System Gray 3
val outlineDark = Color(0xFF38383A)

// --- FOOD CATEGORY PASTELS (Палитра для карточек категорий) ---
// Мягкие цвета для фона продуктов, чтобы на них лежали фото еды

val FoodVeggieLight = Color(0xFFE8F5E9) // Овощи (Светло-зеленый)
val FoodVeggieDark = Color(0xFF1B3022)

val FoodFruitLight = Color(0xFFFFF3E0) // Фрукты (Светло-оранжевый)
val FoodFruitDark = Color(0xFF423018)

val FoodBerryLight = Color(0xFFFCE4EC) // Ягоды (Светло-розовый)
val FoodBerryDark = Color(0xFF401B2C)

val FoodNutLight = Color(0xFFEFEBE9) // Орехи (Светло-коричневый)
val FoodNutDark = Color(0xFF2D2624)

val FoodWaterLight = Color(0xFFE3F2FD) // Напитки (Светло-голубой)
val FoodWaterDark = Color(0xFF182836)

val vitaminColor = Color(0xFF178EC6)