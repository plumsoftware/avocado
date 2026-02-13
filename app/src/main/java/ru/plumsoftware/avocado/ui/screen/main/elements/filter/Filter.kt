package ru.plumsoftware.avocado.ui.screen.main.elements.filter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Filter(
    val id: Int,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val isSelected: Boolean = false
)
