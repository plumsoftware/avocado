package ru.plumsoftware.avocado.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
