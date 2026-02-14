package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc

val allFruits = listOf<Fruit>(
    Fruit(
        id = "banana",
        titleRes = R.string.banana,
        imageRes = R.drawable.banana,
        relatedReceipts = listOf(""),
        vitamins = listOf(),
        kpfc_100g = Kpfc(0, 0, 0, 0),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Fruit(
        id = "banana",
        titleRes = R.string.banana,
        imageRes = R.drawable.banana,
        relatedReceipts = listOf(""),
        vitamins = listOf(),
        kpfc_100g = Kpfc(0, 0, 0, 0),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Fruit(
        id = "banana",
        titleRes = R.string.banana,
        imageRes = R.drawable.banana,
        relatedReceipts = listOf(""),
        vitamins = listOf(),
        kpfc_100g = Kpfc(0, 0, 0, 0),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allBerries = listOf<Berry>(

)

val allNuts = listOf<Nut>(

)