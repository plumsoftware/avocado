package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.data.base.model.healthy.FatKisloty
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.Mineral
import ru.plumsoftware.avocado.data.base.model.healthy.Vitamin

data class Mushroom(
    override val id: String,
    override val titleRes: Int,
    override val descRes: Int,
    override val imageRes: Int,
    override val foodType: FoodType = FoodType.MUSHROOM,
    override val relatedReceipts: List<String>,
    override val vitamins: List<Vitamin>,
    override val kpfc_100g: Kpfc,
    override val timeForFood: TimeForFood,
    override val minerals: List<Mineral>,
    override val fatKisloty: List<FatKisloty>
) : Food