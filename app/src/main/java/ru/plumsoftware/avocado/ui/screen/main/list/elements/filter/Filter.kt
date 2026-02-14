package ru.plumsoftware.avocado.ui.screen.main.list.elements.filter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Filter(
    val id: Int,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val isSelected: Boolean = false
) {
    companion object {
        fun empty() = Filter(
            id = -1,
            title = -1,
            icon = -1,
            isSelected = false
        )
    }
}
