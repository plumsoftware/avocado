package ru.plumsoftware.avocado.ui.screen.main.list

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette

fun getDominantColor(bitmap: Bitmap): Int {
    // Используем Palette API от Android
    val palette = Palette.from(bitmap).generate()
    return palette.getDominantColor(Color.LTGRAY) // Возвращаем доминирующий цвет или серый по умолчанию
}

fun getLightenedColor(color: Int, factor: Float = 0.3f): Int {
    // Осветляем цвет (делаем его пастельным)
    val alpha = Color.alpha(color)
    val red = (Color.red(color) * (1 - factor) + 255 * factor).toInt()
    val green = (Color.green(color) * (1 - factor) + 255 * factor).toInt()
    val blue = (Color.blue(color) * (1 - factor) + 255 * factor).toInt()
    return Color.argb(alpha, red, green, blue)
}

fun getMutedColor(color: Int, saturationFactor: Float = 0.5f): Int {
    // Уменьшаем насыщенность цвета
    val alpha = Color.alpha(color)
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    // Конвертируем в HSV для работы с насыщенностью
    val hsv = FloatArray(3)
    Color.RGBToHSV(red, green, blue, hsv)

    // Уменьшаем насыщенность
    hsv[1] = hsv[1] * saturationFactor

    return Color.HSVToColor(alpha, hsv)
}

/**
 * Возвращает оптимальный цвет для текста/иконок на заданном фоне (черный или белый)
 * Основано на воспринимаемой яркости по формуле WCAG
 */
fun getOnBackgroundColor(background: Int): Int {
    val luminance = calculateLuminance(background)
    return if (luminance > 0.6) Color.BLACK else Color.WHITE
}

/**
 * Возвращает акцентный цвет с хорошим контрастом (дополнительный цвет с коррекцией яркости)
 * Создает визуально привлекательный контраст без резких переходов
 */
fun getAccentOnBackgroundColor(background: Int, saturation: Float = 0.85f): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(background, hsv)

    // Дополнительный оттенок (противоположный в цветовом круге)
    val complementaryHue = (hsv[0] + 180) % 360

    // Коррекция яркости для обеспечения контраста
    val targetValue = if (hsv[2] > 0.6) 0.35f else 0.85f

    // Фиксируем высокую насыщенность для акцента
    return Color.HSVToColor(255, floatArrayOf(complementaryHue, saturation, targetValue))
}

/**
 * Возвращает мягкий контрастный цвет (пастельный акцент)
 * Идеально для второстепенных элементов: бордеров, разделителей
 */
fun getSoftContrastColor(background: Int, lightnessOffset: Float = 0.25f): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(background, hsv)

    // Смещаем оттенок на 30 градусов для мягкого контраста
    val shiftedHue = (hsv[0] + 30) % 360

    // Корректируем яркость в противоположную сторону от фона
    val targetValue = (hsv[2] + if (hsv[2] > 0.5) -lightnessOffset else lightnessOffset)
        .coerceIn(0.2f, 0.9f)

    // Снижаем насыщенность для пастельного эффекта
    val softSaturation = (hsv[1] * 0.6f).coerceAtLeast(0.3f)

    return Color.HSVToColor(255, floatArrayOf(shiftedHue, softSaturation, targetValue))
}

/**
 * Расширенная версия: возвращает набор гармоничных цветов для всего UI
 * [onColor] - для текста/иконок
 * [accentColor] - для кнопок/акцентов
 * [borderColor] - для разделителей/бордеров
 */
data class HarmoniousColors(
    val onColor: Int,
    val accentColor: Int,
    val borderColor: Int
)

fun getHarmoniousColors(background: Int): HarmoniousColors {
    return HarmoniousColors(
        onColor = getOnBackgroundColor(background),
        accentColor = getAccentOnBackgroundColor(background),
        borderColor = getSoftContrastColor(background, lightnessOffset = 0.15f)
    )
}

// Вспомогательные функции
private fun calculateLuminance(color: Int): Double {
    // Упрощенная формула воспринимаемой яркости (без гамма-коррекции для скорости)
    val r = Color.red(color) / 255.0
    val g = Color.green(color) / 255.0
    val b = Color.blue(color) / 255.0
    return 0.299 * r + 0.587 * g + 0.114 * b
}

/**
 * Проверка контрастности по стандарту WCAG 2.1 (для валидации)
 * Возвращает коэффициент контраста (минимум 4.5 для обычного текста)
 */
fun calculateContrastRatio(foreground: Int, background: Int): Double {
    fun relativeLuminance(color: Int): Double {
        fun adjust(c: Double) = if (c <= 0.03928) c / 12.92 else Math.pow((c + 0.055) / 1.055, 2.4)

        val r = adjust(Color.red(color) / 255.0)
        val g = adjust(Color.green(color) / 255.0)
        val b = adjust(Color.blue(color) / 255.0)
        return 0.2126 * r + 0.7152 * g + 0.0722 * b
    }

    val lum1 = relativeLuminance(foreground)
    val lum2 = relativeLuminance(background)
    val brightest = maxOf(lum1, lum2)
    val darkest = minOf(lum1, lum2)

    return (brightest + 0.05) / (darkest + 0.05)
}