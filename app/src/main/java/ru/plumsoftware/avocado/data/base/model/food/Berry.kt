package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.Vitamin

data class Berry(
    override val id: String,
    override val titleRes: Int,
    override val imageRes: Int,
    override val foodType: FoodType,
    override val relatedReceipts: List<String>,
    override val vitamins: List<Vitamin>,
    override val kpfc_100g: Kpfc,
    override val timeForFood: TimeForFood
) : Food