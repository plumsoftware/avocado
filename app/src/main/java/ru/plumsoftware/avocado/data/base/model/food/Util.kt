package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.mineralsMap
import ru.plumsoftware.avocado.data.base.model.healthy.vitaminsMap

val allFruits = listOf<Fruit>(
    // Банан
    Fruit(
        id = "banana",
        titleRes = R.string.banana,
        imageRes = R.drawable.banana,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["manganese"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(96, 0.2, 1.3, 22.0),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Яблоко
    Fruit(
        id = "apple",
        titleRes = R.string.apple,
        imageRes = R.drawable.apple,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(52, 0.2, 0.3, 14.0),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Апельсин
    Fruit(
        id = "orange",
        titleRes = R.string.orange,
        imageRes = R.drawable.orange,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(47, 0.1, 0.9, 11.8),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Киви
    Fruit(
        id = "kiwi",
        titleRes = R.string.kiwi,
        imageRes = R.drawable.kiwi,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(61, 0.5, 1.1, 14.7),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Груша
    Fruit(
        id = "pear",
        titleRes = R.string.pear,
        imageRes = R.drawable.pear,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(57, 0.1, 0.4, 15.0),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Лимон
    Fruit(
        id = "lemon",
        titleRes = R.string.lemon,
        imageRes = R.drawable.lemon,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(29, 0.3, 1.1, 9.3),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Персик
    Fruit(
        id = "peach",
        titleRes = R.string.peach,
        imageRes = R.drawable.peach,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_k"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(39, 0.3, 0.9, 9.5),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Абрикос
    Fruit(
        id = "apricot",
        titleRes = R.string.apricot,
        imageRes = R.drawable.apricot,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(48, 0.4, 1.4, 11.0),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Мандарин
    Fruit(
        id = "mandarin",
        titleRes = R.string.mandarin,
        imageRes = R.drawable.mandarin,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(53, 0.3, 0.8, 13.3),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allBerries = listOf<Berry>(
    // Клубника
    Berry(
        id = "strawberry",
        titleRes = R.string.strawberry,
        imageRes = R.drawable.strawberry,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(32, 0.3, 0.7, 7.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    // Арбуз
    Berry(
        id = "watermelon",
        titleRes = R.string.watermelon,
        imageRes = R.drawable.watermelon,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(30, 0.2, 0.6, 8.0),
        timeForFood = TimeForFood.BREAKFAST
    ),
    // Виноград
    Berry(
        id = "grape",
        titleRes = R.string.grape,
        imageRes = R.drawable.grape,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(69, 0.2, 0.7, 18.0),
        timeForFood = TimeForFood.BREAKFAST
    ),
)

val allNuts = listOf<Nut>(

)