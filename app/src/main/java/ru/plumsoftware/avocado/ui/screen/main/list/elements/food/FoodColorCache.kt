package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import android.content.Context
import android.graphics.BitmapFactory
import ru.plumsoftware.avocado.ui.screen.main.list.HarmoniousColors
import ru.plumsoftware.avocado.ui.screen.main.list.getDominantColor
import ru.plumsoftware.avocado.ui.screen.main.list.getHarmoniousColors
import ru.plumsoftware.avocado.ui.screen.main.list.getMutedColor

class FoodColorCache {
    private val cache = mutableMapOf<Int, Int>() // key: imageRes, value: backgroundColor
    private val harmoniousColorsCache = mutableMapOf<Int, HarmoniousColors>()

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

    fun getHarmoniousColors(imageRes: Int, context: Context): HarmoniousColors {
        val background = getBackgroundColor(imageRes, context)
        return harmoniousColorsCache.getOrPut(imageRes) {
            getHarmoniousColors(background)
        }
    }
}