package ru.plumsoftware.avocado.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.plumsoftware.avocado.BuildConfig
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

// 1. Декодер Base64
fun dataUrlToBitmap(dataUrl: String): Bitmap? {
    if (dataUrl.isBlank()) return null
    val base64Prefix = "base64,"
    val base64Index = dataUrl.indexOf(base64Prefix)

    val base64Data = if (base64Index != -1) {
        dataUrl.substring(base64Index + base64Prefix.length)
    } else dataUrl // Если префикса нет, пробуем декодировать как есть

    return try {
        val decodedBytes = Base64.decode(base64Data, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}

// 2. Проверка дат (формат "yyyy-MM-dd")
fun isPromoActive(startDate: String, endDate: String): Boolean {
    if (startDate.isBlank() || endDate.isBlank()) return false
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = formatter.parse(startDate)
        val end = formatter.parse(endDate)

        // Текущая дата, но мы "обнуляем" ей часы/минуты,
        // прогоняя через форматтер туда-обратно
        val todayStr = formatter.format(Date())
        val today = formatter.parse(todayStr)

        if (start == null || end == null || today == null) return false

        // Сегодня >= start И сегодня <= end
        !today.before(start) && !today.after(end)
    } catch (e: Exception) {
        false
    }
}

fun Activity.getBottomInset(): Int {
    val rootView = window.decorView
    val insets = ViewCompat.getRootWindowInsets(rootView)
    return insets?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: 0
}

fun Activity.getTopInset(): Int {
    val rootView = window.decorView
    val insets = ViewCompat.getRootWindowInsets(rootView)
    return insets?.getInsets(WindowInsetsCompat.Type.systemBars())?.top ?: 0
}

@Composable
fun Activity.getBottomInsetInDp(): Dp {
    return with(LocalDensity.current) {
        getBottomInset().toDp()
    }
}

@Composable
fun Activity.getTopInsetInDp(): Dp {
    return with(LocalDensity.current) {
        getTopInset().toDp()
    }
}

inline fun Any.log(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.name, message)
    }
}
