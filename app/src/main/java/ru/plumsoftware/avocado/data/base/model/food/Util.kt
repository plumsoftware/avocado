package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.fatsMap
import ru.plumsoftware.avocado.data.base.model.healthy.mineralsMap
import ru.plumsoftware.avocado.data.base.model.healthy.vitaminsMap

val allFruits = listOf<Fruit>(
    Fruit(
        id = "banana",
        titleRes = R.string.banana,
        descRes = R.string.desc_banana,
        imageRes = R.drawable.banana,
        relatedReceipts = listOf("green_smoothie", "fruit_salad", "peanut_power"),
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
    Fruit(
        id = "apple",
        titleRes = R.string.apple,
        descRes = R.string.desc_apple,
        imageRes = R.drawable.apple,
        relatedReceipts = listOf("green_smoothie", "fruit_salad", "vitamin_glow", "beet_detox", "baked_apples"),
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
    Fruit(
        id = "orange",
        titleRes = R.string.orange,
        descRes = R.string.desc_orange,
        imageRes = R.drawable.orange,
        relatedReceipts = listOf("tropical_paradise", "vitamin_glow"),
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
    Fruit(
        id = "kiwi",
        titleRes = R.string.kiwi,
        descRes = R.string.desc_kiwi,
        imageRes = R.drawable.kiwi,
        relatedReceipts = listOf("green_smoothie", "fruit_salad"),
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
    Fruit(
        id = "pear",
        titleRes = R.string.pear,
        descRes = R.string.desc_pear,
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
    Fruit(
        id = "lemon",
        titleRes = R.string.lemon,
        descRes = R.string.desc_lemon,
        imageRes = R.drawable.lemon,
        relatedReceipts = listOf("baked_salmon", "beet_detox", "garlic_shrimp"),
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
    Fruit(
        id = "peach",
        titleRes = R.string.peach,
        descRes = R.string.desc_peach,
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
    Fruit(
        id = "apricot",
        titleRes = R.string.apricot,
        descRes = R.string.desc_apricot,
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
    Fruit(
        id = "mandarin",
        titleRes = R.string.mandarin,
        descRes = R.string.desc_mandarin,
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
    ),
    Fruit(
        id = "pineapple",
        titleRes = R.string.pineapple,
        descRes = R.string.desc_pineapple,
        imageRes = R.drawable.pineapple,
        relatedReceipts = listOf("tropical_paradise"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(50, 0.1, 0.5, 13.1),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Fruit(
        id = "pomegranate",
        titleRes = R.string.pomegranate,
        descRes = R.string.desc_pomegranate,
        imageRes = R.drawable.pomegranate,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(83, 1.2, 1.7, 18.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Fruit(
        id = "mango",
        titleRes = R.string.mango,
        descRes = R.string.desc_mango,
        imageRes = R.drawable.mango,
        relatedReceipts = listOf("tropical_paradise"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(60, 0.4, 0.8, 15.0),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Fruit(
        id = "persimmon",
        titleRes = R.string.persimmon,
        descRes = R.string.desc_persimmon,
        imageRes = R.drawable.persimmon,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(70, 0.2, 0.6, 18.6),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allBerries = listOf<Berry>(
    Berry(
        id = "strawberry",
        titleRes = R.string.strawberry,
        descRes = R.string.desc_strawberry,
        imageRes = R.drawable.strawberry,
        relatedReceipts = listOf("berry_blast"),
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
    Berry(
        id = "blueberry",
        titleRes = R.string.blueberry,
        descRes = R.string.desc_blueberry,
        imageRes = R.drawable.blueberry,
        relatedReceipts = listOf("berry_blast"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(57, 0.3, 0.7, 14.5),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "watermelon",
        titleRes = R.string.watermelon,
        descRes = R.string.desc_watermelon,
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
    Berry(
        id = "grape",
        titleRes = R.string.grape,
        descRes = R.string.desc_grape,
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
    Berry(
        id = "avocado",
        titleRes = R.string.avocado,
        descRes = R.string.desc_avocado,
        imageRes = R.drawable.avocado,
        relatedReceipts = listOf("avocado_toast", "green_smoothie"),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(160, 14.7, 2.0, 8.5),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "raspberry",
        titleRes = R.string.raspberry,
        descRes = R.string.desc_raspberry,
        imageRes = R.drawable.raspberry,
        relatedReceipts = listOf("berry_blast"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(52, 0.7, 1.2, 11.9),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "blackberry",
        titleRes = R.string.blackberry,
        descRes = R.string.desc_blackberry,
        imageRes = R.drawable.blackberry,
        relatedReceipts = listOf("berry_blast"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(43, 0.5, 1.4, 10.2),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "currant",
        titleRes = R.string.currant,
        descRes = R.string.desc_currant,
        imageRes = R.drawable.currant,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b5"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(44, 0.4, 1.4, 10.0),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "cranberry",
        titleRes = R.string.cranberry,
        descRes = R.string.desc_cranberry,
        imageRes = R.drawable.cranberry,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_k"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(46, 0.1, 0.4, 12.2),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Berry(
        id = "cherry",
        titleRes = R.string.cherry,
        descRes = R.string.desc_cherry,
        imageRes = R.drawable.cherry,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_k"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(50, 0.3, 1.0, 12.0),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allNuts = listOf<Nut>(
    Nut(
        id = "walnut",
        titleRes = R.string.walnut,
        descRes = R.string.desc_walnut,
        imageRes = R.drawable.walnut,
        relatedReceipts = listOf("nut_energy_mix", "baked_apples"),
        vitamins = listOf(
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(654, 65.2, 15.2, 13.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "almond",
        titleRes = R.string.almond,
        descRes = R.string.desc_almond,
        imageRes = R.drawable.almond,
        relatedReceipts = listOf("peanut_power", "nut_energy_mix"),
        vitamins = listOf(
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b2"]!!,
            vitaminsMap["vitamin_b3"]!!
        ),
        minerals = listOf(
            mineralsMap["magnesium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["calcium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(579, 49.9, 21.2, 21.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "hazelnut",
        titleRes = R.string.hazelnut,
        descRes = R.string.desc_hazelnut,
        imageRes = R.drawable.hazelnut,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(628, 60.8, 15.0, 16.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "cashew",
        titleRes = R.string.cashew,
        descRes = R.string.desc_cashew,
        imageRes = R.drawable.cashew,
        relatedReceipts = listOf("nut_energy_mix"),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(553, 43.8, 18.2, 30.2),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "peanut",
        titleRes = R.string.peanut,
        descRes = R.string.desc_peanut,
        imageRes = R.drawable.peanut,
        relatedReceipts = listOf("peanut_power"),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(567, 49.2, 25.8, 16.1),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "pistachio",
        titleRes = R.string.pistachio,
        descRes = R.string.desc_pistachio,
        imageRes = R.drawable.pistachio,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(560, 45.3, 20.6, 27.5),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "pine_nut",
        titleRes = R.string.pine_nut,
        descRes = R.string.desc_pine_nut,
        imageRes = R.drawable.pine_nut,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(673, 68.4, 13.7, 13.1),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "pecan",
        titleRes = R.string.pecan,
        descRes = R.string.desc_pecan,
        imageRes = R.drawable.pecan,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_e"]!!,
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(691, 72.0, 9.2, 13.9),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "macadamia",
        titleRes = R.string.macadamia,
        descRes = R.string.desc_macadamia,
        imageRes = R.drawable.macadamia,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_b1"]!!, vitaminsMap["vitamin_b6"]!!),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(718, 75.8, 7.9, 13.8),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Nut(
        id = "chestnut",
        titleRes = R.string.chestnut,
        descRes = R.string.desc_chestnut,
        imageRes = R.drawable.chestnut,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(213, 2.3, 2.4, 45.5),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allVegetables = listOf<Vegetable>(
    Vegetable(
        id = "potato",
        titleRes = R.string.potato,
        descRes = R.string.desc_potato,
        imageRes = R.drawable.potato,
        relatedReceipts = listOf("mushroom_soup"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(77, 0.1, 2.0, 17.0, fiber = 2.2),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "carrot",
        titleRes = R.string.carrot,
        descRes = R.string.desc_carrot,
        imageRes = R.drawable.carrot,
        relatedReceipts = listOf("vitamin_glow"),
        vitamins = listOf(
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(41, 0.2, 0.9, 9.6, fiber = 2.8),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "onion",
        titleRes = R.string.onion,
        descRes = R.string.desc_onion,
        imageRes = R.drawable.onion,
        relatedReceipts = listOf("mushroom_soup", "beef_steak_veggies"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b1"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(40, 0.1, 1.1, 9.3, fiber = 1.7),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "garlic",
        titleRes = R.string.garlic,
        descRes = R.string.desc_garlic,
        imageRes = R.drawable.garlic,
        relatedReceipts = listOf("chicken_broccoli", "ratatouille", "turkey_meatballs", "garlic_shrimp"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(149, 0.5, 6.4, 33.1, fiber = 4.5),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "tomato",
        titleRes = R.string.tomato,
        descRes = R.string.desc_tomato,
        imageRes = R.drawable.tomato,
        relatedReceipts = listOf("ratatouille", "greek_salad", "beef_steak_veggies", "spinach_omelet"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(18, 0.2, 0.9, 3.9, fiber = 1.2),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "cucumber",
        titleRes = R.string.cucumber,
        descRes = R.string.desc_cucumber,
        imageRes = R.drawable.cucumber,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b5"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(15, 0.1, 0.7, 3.6, fiber = 0.5),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "cabbage",
        titleRes = R.string.cabbage,
        descRes = R.string.desc_cabbage,
        imageRes = R.drawable.cabbage,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(25, 0.1, 1.3, 5.8, fiber = 2.5),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "broccoli",
        titleRes = R.string.broccoli,
        descRes = R.string.desc_broccoli,
        imageRes = R.drawable.broccoli,
        relatedReceipts = listOf("chicken_broccoli"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["calcium"]!!
        ),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(34, 0.4, 2.8, 6.6, fiber = 2.6),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "bell_pepper",
        titleRes = R.string.bell_pepper,
        descRes = R.string.desc_bell_pepper,
        imageRes = R.drawable.bell_pepper,
        relatedReceipts = listOf("beef_steak_veggies"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(20, 0.2, 0.9, 4.6, fiber = 1.7),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "beet",
        titleRes = R.string.beet,
        descRes = R.string.desc_beet,
        imageRes = R.drawable.beet,
        relatedReceipts = listOf("beet_detox"),
        vitamins = listOf(
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(43, 0.2, 1.6, 9.6, fiber = 2.8),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "pumpkin",
        titleRes = R.string.pumpkin,
        descRes = R.string.desc_pumpkin,
        imageRes = R.drawable.pumpkin,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b2"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(26, 0.1, 1.0, 6.5, fiber = 1.5),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "zucchini",
        titleRes = R.string.zucchini,
        descRes = R.string.desc_zucchini,
        imageRes = R.drawable.zucchini,
        relatedReceipts = listOf("ratatouille", "turkey_meatballs"),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b2"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(17, 0.3, 1.2, 3.1, fiber = 1.1),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "eggplant",
        titleRes = R.string.eggplant,
        descRes = R.string.desc_eggplant,
        imageRes = R.drawable.eggplant,
        relatedReceipts = listOf("ratatouille"),
        vitamins = listOf(
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(25, 0.2, 1.0, 5.9, fiber = 3.0),
        timeForFood = TimeForFood.DINNER
    ),
    Vegetable(
        id = "radish",
        titleRes = R.string.radish,
        descRes = R.string.desc_radish,
        imageRes = R.drawable.radish,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b9"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["calcium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(16, 0.1, 0.7, 3.4, fiber = 1.6),
        timeForFood = TimeForFood.BREAKFAST
    ),
    Vegetable(
        id = "spinach",
        titleRes = R.string.spinach,
        descRes = R.string.desc_spinach,
        imageRes = R.drawable.spinach,
        relatedReceipts = listOf("green_smoothie", "spinach_omelet"),
        vitamins = listOf(
            vitaminsMap["vitamin_k"]!!,
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_b9"]!!,
            vitaminsMap["vitamin_b2"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["magnesium"]!!,
            mineralsMap["iron"]!!,
            mineralsMap["calcium"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(23, 0.4, 2.9, 3.6, fiber = 2.2),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allMeat = listOf<Meat>(
    Meat(
        id = "chicken_breast",
        titleRes = R.string.chicken_breast,
        descRes = R.string.desc_chicken_breast,
        imageRes = R.drawable.chicken_2,
        foodType = FoodType.MEAT,
        relatedReceipts = listOf("chicken_broccoli"),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["phosphorus"]!!,
            mineralsMap["zinc"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(165, 3.6, 31.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "chicken_thigh",
        titleRes = R.string.chicken_thigh,
        descRes = R.string.desc_chicken_thigh,
        imageRes = R.drawable.chicken,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["zinc"]!!, mineralsMap["iron"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(209, 11.0, 26.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "turkey_breast",
        titleRes = R.string.turkey_breast,
        descRes = R.string.desc_turkey_breast,
        imageRes = R.drawable.turkey_breast,
        foodType = FoodType.MEAT,
        relatedReceipts = listOf("turkey_meatballs"),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["phosphorus"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(135, 1.0, 30.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "beef_lean",
        titleRes = R.string.beef_lean,
        descRes = R.string.desc_beef_lean,
        imageRes = R.drawable.steak,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["iron"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["selenium"]!!,
            mineralsMap["phosphorus"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(250, 15.0, 26.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "beef_ribeye",
        titleRes = R.string.beef_ribeye,
        descRes = R.string.desc_beef_ribeye,
        imageRes = R.drawable.steak,
        foodType = FoodType.MEAT,
        relatedReceipts = listOf("beef_steak_veggies"),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(mineralsMap["iron"]!!, mineralsMap["zinc"]!!, mineralsMap["selenium"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(291, 22.0, 24.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "pork_tenderloin",
        titleRes = R.string.pork_tenderloin,
        descRes = R.string.desc_pork_tenderloin,
        imageRes = R.drawable.pork_tenderloin,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["phosphorus"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(143, 3.5, 26.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "pork_neck",
        titleRes = R.string.pork_neck,
        descRes = R.string.desc_pork_neck,
        imageRes = R.drawable.steak,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["zinc"]!!, mineralsMap["iron"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(242, 18.0, 20.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "lamb",
        titleRes = R.string.lamb,
        descRes = R.string.desc_lamb,
        imageRes = R.drawable.steak,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(mineralsMap["iron"]!!, mineralsMap["zinc"]!!, mineralsMap["selenium"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(258, 18.0, 23.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "veal",
        titleRes = R.string.veal,
        descRes = R.string.desc_veal,
        imageRes = R.drawable.steak,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["iron"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["phosphorus"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(172, 7.0, 25.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "rabbit",
        titleRes = R.string.rabbit,
        descRes = R.string.desc_rabbit,
        imageRes = R.drawable.rabbit,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["phosphorus"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(173, 7.0, 26.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "duck",
        titleRes = R.string.duck,
        descRes = R.string.desc_duck,
        imageRes = R.drawable.duck,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["zinc"]!!, mineralsMap["iron"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(337, 28.0, 19.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),
    Meat(
        id = "goose",
        titleRes = R.string.goose,
        descRes = R.string.desc_goose,
        imageRes = R.drawable.chicken,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["zinc"]!!, mineralsMap["iron"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(371, 33.0, 18.0, 0.0),
        timeForFood = TimeForFood.DINNER
    )
)

val allFish = listOf<Fish>(
    Fish(
        id = "salmon",
        titleRes = R.string.salmon,
        descRes = R.string.desc_salmon,
        imageRes = R.drawable.salmon,
        relatedReceipts = listOf("baked_salmon"),
        vitamins = listOf(
            vitaminsMap["vitamin_d"]!!,
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["phosphorus"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(208, 13.0, 20.0, 0.0, omega3 = 2.3),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "mackerel",
        titleRes = R.string.mackerel,
        descRes = R.string.desc_mackerel,
        imageRes = R.drawable.mackerel,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_d"]!!,
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!
        ),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["phosphorus"]!!),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(305, 25.0, 19.0, 0.0, omega3 = 4.1),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "herring",
        titleRes = R.string.herring,
        descRes = R.string.desc_herring,
        imageRes = R.drawable.herring,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_d"]!!, vitaminsMap["vitamin_b12"]!!),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["phosphorus"]!!),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(158, 9.0, 18.0, 0.0, omega3 = 1.7),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "tuna",
        titleRes = R.string.tuna,
        descRes = R.string.desc_tuna,
        imageRes = R.drawable.tuna,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_b12"]!!, vitaminsMap["vitamin_b3"]!!),
        minerals = listOf(mineralsMap["selenium"]!!, mineralsMap["phosphorus"]!!),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(132, 1.0, 29.0, 0.0, omega3 = 0.3),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "trout",
        titleRes = R.string.trout,
        descRes = R.string.desc_trout,
        imageRes = R.drawable.trout,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_d"]!!, vitaminsMap["vitamin_b12"]!!),
        minerals = listOf(mineralsMap["selenium"]!!),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(190, 12.0, 20.0, 0.0, omega3 = 1.0),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "sardine",
        titleRes = R.string.sardine,
        descRes = R.string.desc_sardine,
        imageRes = R.drawable.sardine,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_d"]!!, vitaminsMap["vitamin_b12"]!!),
        minerals = listOf(mineralsMap["calcium"]!!, mineralsMap["selenium"]!!),
        fatKisloty = listOf(fatsMap["omega3"]!!),
        kpfc_100g = Kpfc(208, 11.0, 25.0, 0.0, omega3 = 1.5),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "cod",
        titleRes = R.string.cod,
        descRes = R.string.desc_cod,
        imageRes = R.drawable.cod,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_b6"]!!, vitaminsMap["vitamin_b12"]!!),
        minerals = listOf(mineralsMap["phosphorus"]!!, mineralsMap["selenium"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(82, 0.7, 18.0, 0.0, omega3 = 0.2),
        timeForFood = TimeForFood.DINNER
    ),
    Fish(
        id = "pollock",
        titleRes = R.string.pollock,
        descRes = R.string.desc_pollock,
        imageRes = R.drawable.pollock,
        relatedReceipts = emptyList(),
        vitamins = listOf(vitaminsMap["vitamin_b12"]!!),
        minerals = listOf(mineralsMap["selenium"]!!),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(92, 1.0, 19.0, 0.0, omega3 = 0.5),
        timeForFood = TimeForFood.DINNER
    ),
    // Креветки
    Fish(
        id = "shrimp",
        titleRes = R.string.shrimp,
        descRes = R.string.desc_shrimp,
        imageRes = R.drawable.shrimp,
        foodType = FoodType.FISH,
        relatedReceipts = listOf("garlic_shrimp"),
        vitamins = listOf(
            vitaminsMap["vitamin_b12"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_e"]!!
        ),
        minerals = listOf(
            mineralsMap["iodine"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["selenium"]!!,
            mineralsMap["phosphorus"]!!
        ),
        fatKisloty = listOf(
            fatsMap["omega3"]!!
        ),
        kpfc_100g = Kpfc(
            kals = 87,
            proteins = 18.0,
            fats = 1.2,
            carbohydrates = 0.2,
            omega3 = 0.3
        ),
        timeForFood = TimeForFood.DINNER
    )
)

val allMushrooms = listOf<Mushroom>(
    Mushroom(
        id = "champignon",
        titleRes = R.string.champignon,
        descRes = R.string.desc_champignon,
        imageRes = R.drawable.champignon,
        relatedReceipts = listOf("mushroom_soup"),
        vitamins = listOf(
            vitaminsMap["vitamin_b2"]!!,
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b5"]!!,
            vitaminsMap["vitamin_d"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["phosphorus"]!!,
            mineralsMap["selenium"]!!,
            mineralsMap["copper"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(27, 1.0, 4.3, 0.1, fiber = 2.0),
        timeForFood = TimeForFood.LUNCH
    ),
    Mushroom(
        id = "white_mushroom",
        titleRes = R.string.white_mushroom,
        descRes = R.string.desc_white_mushroom,
        imageRes = R.drawable.white_mushroom,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b2"]!!,
            vitaminsMap["vitamin_c"]!!,
            vitaminsMap["vitamin_d"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["potassium"]!!,
            mineralsMap["manganese"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(34, 1.7, 3.7, 1.1, fiber = 3.0),
        timeForFood = TimeForFood.LUNCH
    ),
    Mushroom(
        id = "chanterelle",
        titleRes = R.string.chanterelle,
        descRes = R.string.desc_chanterelle,
        imageRes = R.drawable.chanterelle,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_a"]!!,
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_d"]!!,
            vitaminsMap["vitamin_c"]!!
        ),
        minerals = listOf(
            mineralsMap["copper"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["potassium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(19, 0.5, 1.5, 1.0, fiber = 1.0),
        timeForFood = TimeForFood.LUNCH
    ),
    Mushroom(
        id = "oyster_mushroom",
        titleRes = R.string.oyster_mushroom,
        descRes = R.string.desc_oyster_mushroom,
        imageRes = R.drawable.oyster_mushroom,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b5"]!!,
            vitaminsMap["vitamin_d"]!!
        ),
        minerals = listOf(
            mineralsMap["potassium"]!!,
            mineralsMap["phosphorus"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(33, 0.4, 3.3, 6.0, fiber = 2.3),
        timeForFood = TimeForFood.DINNER
    ),
    Mushroom(
        id = "shiitake",
        titleRes = R.string.shiitake,
        descRes = R.string.desc_shiitake,
        imageRes = R.drawable.shiitake,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_d"]!!,
            vitaminsMap["vitamin_b5"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["manganese"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(34, 0.5, 2.2, 6.8, fiber = 2.5),
        timeForFood = TimeForFood.LUNCH
    )
)

val allFood = allFruits + allBerries + allNuts + allVegetables + allMeat + allFish + allMushrooms