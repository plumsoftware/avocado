package ru.plumsoftware.avocado.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.plumsoftware.avocado.BuildConfig
import kotlin.io.encoding.Base64

@Composable
fun dataUrlToBitmap(dataUrl: String): Bitmap? {
    // Проверяем, что строка начинается с "data:image/"
    val base64Prefix = "base64,"
    val base64Index = dataUrl.indexOf(base64Prefix)
    if (base64Index == -1) return null

    // Берём только Base64 часть
    val base64Data = dataUrl.substring(base64Index + base64Prefix.length)

    return try {
        val decodedBytes = Base64.decode(base64Data)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
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
