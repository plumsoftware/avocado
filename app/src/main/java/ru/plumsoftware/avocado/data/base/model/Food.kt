package ru.plumsoftware.avocado.data.base.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface Food {
    val foodType: FoodType
    @get:StringRes val titleRes: Int
    @get:DrawableRes val imageRes: Int
}