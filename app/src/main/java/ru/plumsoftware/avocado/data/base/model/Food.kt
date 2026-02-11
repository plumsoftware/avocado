package ru.plumsoftware.avocado.data.base.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface Food {
    val id: String
    @get:StringRes val titleRes: Int
    @get:DrawableRes val imageRes: Int
    val foodType: FoodType
}