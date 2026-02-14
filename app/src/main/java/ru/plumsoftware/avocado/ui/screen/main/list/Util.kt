package ru.plumsoftware.avocado.ui.screen.main.list

import android.graphics.Bitmap
import android.graphics.Color
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