package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import android.content.Context
import android.graphics.BitmapFactory
import ru.plumsoftware.avocado.ui.screen.main.list.getDominantColor
import ru.plumsoftware.avocado.ui.screen.main.list.getMutedColor

class FoodColorCache {
    private val cache = mutableMapOf<Int, Int>() // key: imageRes, value: backgroundColor

    fun getBackgroundColor(imageRes: Int, context: Context): Int {
        return cache.getOrPut(imageRes) {
            // Загружаем bitmap из ресурсов
            val bitmap = BitmapFactory.decodeResource(context.resources, imageRes)
            val dominantColor = getDominantColor(bitmap)
            bitmap.recycle()

            // Делаем цвет пастельным/приглушенным
            getMutedColor(dominantColor, 0.3f)
        }
    }
}