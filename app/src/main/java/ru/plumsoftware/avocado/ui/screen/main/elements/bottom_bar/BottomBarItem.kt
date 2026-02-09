package ru.plumsoftware.avocado.ui.screen.main.elements.bottom_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    @param:StringRes val title: Int,
    @param:DrawableRes val iconRes: Int? = null,
    val iconVector: ImageVector? = null
)
