package ru.plumsoftware.avocado.data.base.model.food

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.Vitamin

interface Food {
    val id: String
    @get:StringRes
    val titleRes: Int
    @get:DrawableRes
    val imageRes: Int
    val foodType: FoodType
    val relatedReceipts: List<String>

    val vitamins: List<Vitamin>
    val kpfc_100g: Kpfc

    val timeForFood: TimeForFood
}