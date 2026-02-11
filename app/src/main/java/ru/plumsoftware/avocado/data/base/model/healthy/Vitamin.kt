package ru.plumsoftware.avocado.data.base.model.healthy

import androidx.annotation.StringRes

interface Vitamin {
    val id: String
    @get:StringRes val title: Int
}