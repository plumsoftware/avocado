package ru.plumsoftware.avocado.data.base.model.healthy

import androidx.annotation.StringRes

class FatKisloty(
    val id: String,
    @get:StringRes val title: Int,
    @get:StringRes val healthyFor: Int = 0
)