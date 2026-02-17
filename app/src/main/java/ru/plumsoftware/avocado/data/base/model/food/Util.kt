package ru.plumsoftware.avocado.data.base.model.food

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.data.base.model.healthy.fatsMap
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
    ),

    // Ананас
    Fruit(
        id = "pineapple",
        titleRes = R.string.pineapple,
        imageRes = R.drawable.pineapple,
        relatedReceipts = emptyList(),
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

    // Гранат
    Fruit(
        id = "pomegranate",
        titleRes = R.string.pomegranate,
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

    // Манго
    Fruit(
        id = "mango",
        titleRes = R.string.mango,
        imageRes = R.drawable.mango,
        relatedReceipts = emptyList(),
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

    // Хурма
    Fruit(
        id = "persimmon",
        titleRes = R.string.persimmon,
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

    // Голубика
    Berry(
        id = "blueberry",
        titleRes = R.string.blueberry,
        imageRes = R.drawable.blueberry,
        relatedReceipts = emptyList(),
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

    // Авокадо
    Berry(
        id = "avocado",
        titleRes = R.string.avocado,
        imageRes = R.drawable.avocado,
        relatedReceipts = emptyList(),
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
        fatKisloty = listOf(
            fatsMap["omega3"]!!
        ),
        kpfc_100g = Kpfc(160, 14.7, 2.0, 8.5),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Малина
    Berry(
        id = "raspberry",
        titleRes = R.string.raspberry,
        imageRes = R.drawable.raspberry,
        relatedReceipts = emptyList(),
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

    // Ежевика
    Berry(
        id = "blackberry",
        titleRes = R.string.blackberry,
        imageRes = R.drawable.blackberry,
        relatedReceipts = emptyList(),
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

    // Смородина (черная)
    Berry(
        id = "currant",
        titleRes = R.string.currant,
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

    // Клюква
    Berry(
        id = "cranberry",
        titleRes = R.string.cranberry,
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

    // Вишня
    Berry(
        id = "cherry",
        titleRes = R.string.cherry,
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
    // Грецкий орех
    Nut(
        id = "walnut",
        titleRes = R.string.walnut,
        imageRes = R.drawable.walnut,
        relatedReceipts = emptyList(),
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
        fatKisloty = listOf(
            fatsMap["omega3"]!!
        ),
        kpfc_100g = Kpfc(654, 65.2, 15.2, 13.7),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Миндаль
    Nut(
        id = "almond",
        titleRes = R.string.almond,
        imageRes = R.drawable.almond,
        relatedReceipts = emptyList(),
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

    // Фундук
    Nut(
        id = "hazelnut",
        titleRes = R.string.hazelnut,
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

    // Кешью
    Nut(
        id = "cashew",
        titleRes = R.string.cashew,
        imageRes = R.drawable.cashew,
        relatedReceipts = emptyList(),
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

    // Арахис (технически бобовые, но часто относят к орехам)
    Nut(
        id = "peanut",
        titleRes = R.string.peanut,
        imageRes = R.drawable.peanut,
        relatedReceipts = emptyList(),
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

    // Фисташки
    Nut(
        id = "pistachio",
        titleRes = R.string.pistachio,
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

    // Кедровый орех
    Nut(
        id = "pine_nut",
        titleRes = R.string.pine_nut,
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

    // Бразильский орех
//    Nut(
//        id = "brazil_nut",
//        titleRes = R.string.brazil_nut,
//        imageRes = R.drawable.brazil_nut,
//        relatedReceipts = emptyList(),
//        vitamins = listOf(
//            vitaminsMap["vitamin_e"]!!,
//            vitaminsMap["vitamin_b1"]!!,
//            vitaminsMap["vitamin_b6"]!!
//        ),
//        minerals = listOf(
//            mineralsMap["selenium"]!!, // Бразильский орех - рекордсмен по селену
//            mineralsMap["magnesium"]!!,
//            mineralsMap["copper"]!!,
//            mineralsMap["zinc"]!!
//        ),
//        fatKisloty = emptyList(),
//        kpfc_100g = Kpfc(659, 67.1, 14.3, 11.7),
//        timeForFood = TimeForFood.BREAKFAST
//    ),

    // Пекан
    Nut(
        id = "pecan",
        titleRes = R.string.pecan,
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

    // Макадамия
    Nut(
        id = "macadamia",
        titleRes = R.string.macadamia,
        imageRes = R.drawable.macadamia,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b1"]!!,
            vitaminsMap["vitamin_b6"]!!
        ),
        minerals = listOf(
            mineralsMap["manganese"]!!,
            mineralsMap["copper"]!!,
            mineralsMap["magnesium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(718, 75.8, 7.9, 13.8),
        timeForFood = TimeForFood.BREAKFAST
    ),

    // Каштан (технически орех, но сильно отличается по составу)
    Nut(
        id = "chestnut",
        titleRes = R.string.chestnut,
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
    // Картофель
    Vegetable(
        id = "potato",
        titleRes = R.string.potato,
        imageRes = R.drawable.potato,
        relatedReceipts = emptyList(),
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

    // Морковь
    Vegetable(
        id = "carrot",
        titleRes = R.string.carrot,
        imageRes = R.drawable.carrot,
        relatedReceipts = emptyList(),
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

    // Лук репчатый
    Vegetable(
        id = "onion",
        titleRes = R.string.onion,
        imageRes = R.drawable.onion,
        relatedReceipts = emptyList(),
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

    // Чеснок
    Vegetable(
        id = "garlic",
        titleRes = R.string.garlic,
        imageRes = R.drawable.garlic,
        relatedReceipts = emptyList(),
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

    // Томат (помидор)
    Vegetable(
        id = "tomato",
        titleRes = R.string.tomato,
        imageRes = R.drawable.tomato,
        relatedReceipts = emptyList(),
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

    // Огурец
    Vegetable(
        id = "cucumber",
        titleRes = R.string.cucumber,
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

    // Капуста белокочанная
    Vegetable(
        id = "cabbage",
        titleRes = R.string.cabbage,
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

    // Брокколи
    Vegetable(
        id = "broccoli",
        titleRes = R.string.broccoli,
        imageRes = R.drawable.broccoli,
        relatedReceipts = emptyList(),
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
        fatKisloty = listOf(
            fatsMap["omega3"]!!
        ),
        kpfc_100g = Kpfc(34, 0.4, 2.8, 6.6, fiber = 2.6),
        timeForFood = TimeForFood.DINNER
    ),

    // Перец болгарский
    Vegetable(
        id = "bell_pepper",
        titleRes = R.string.bell_pepper,
        imageRes = R.drawable.bell_pepper,
        relatedReceipts = emptyList(),
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

    // Свекла
    Vegetable(
        id = "beet",
        titleRes = R.string.beet,
        imageRes = R.drawable.beet,
        relatedReceipts = emptyList(),
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

    // Тыква
    Vegetable(
        id = "pumpkin",
        titleRes = R.string.pumpkin,
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

    // Кабачок
    Vegetable(
        id = "zucchini",
        titleRes = R.string.zucchini,
        imageRes = R.drawable.zucchini,
        relatedReceipts = emptyList(),
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

    // Баклажан
    Vegetable(
        id = "eggplant",
        titleRes = R.string.eggplant,
        imageRes = R.drawable.eggplant,
        relatedReceipts = emptyList(),
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

    // Редис
    Vegetable(
        id = "radish",
        titleRes = R.string.radish,
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

    // Шпинат
    Vegetable(
        id = "spinach",
        titleRes = R.string.spinach,
        imageRes = R.drawable.spinach,
        relatedReceipts = emptyList(),
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
        fatKisloty = listOf(
            fatsMap["omega3"]!!
        ),
        kpfc_100g = Kpfc(23, 0.4, 2.9, 3.6, fiber = 2.2),
        timeForFood = TimeForFood.BREAKFAST
    )
)

val allMeat = listOf<Meat>(
    // Куриная грудка
    Meat(
        id = "chicken_breast",
        titleRes = R.string.chicken_breast,
        imageRes = R.drawable.chicken,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
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

    // Куриное бедро
    Meat(
        id = "chicken_thigh",
        titleRes = R.string.chicken_thigh,
        imageRes = R.drawable.chicken,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(209, 11.0, 26.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),

    // Индейка (грудка)
    Meat(
        id = "turkey_breast",
        titleRes = R.string.turkey_breast,
        imageRes = R.drawable.meat_1,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
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

    // Говядина (постная)
    Meat(
        id = "beef_lean",
        titleRes = R.string.beef_lean,
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

    // Говяжий стейк (рибай)
    Meat(
        id = "beef_ribeye",
        titleRes = R.string.beef_ribeye,
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
            mineralsMap["selenium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(291, 22.0, 24.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),

    // Свинина (постная, вырезка)
    Meat(
        id = "pork_tenderloin",
        titleRes = R.string.pork_tenderloin,
        imageRes = R.drawable.steak,
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

    // Свинина (шея)
    Meat(
        id = "pork_neck",
        titleRes = R.string.pork_neck,
        imageRes = R.drawable.steak,
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
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(242, 18.0, 20.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),

    // Баранина
    Meat(
        id = "lamb",
        titleRes = R.string.lamb,
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
            mineralsMap["selenium"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(258, 18.0, 23.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),

    // Телятина
    Meat(
        id = "veal",
        titleRes = R.string.veal,
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

    // Кролик
    Meat(
        id = "rabbit",
        titleRes = R.string.rabbit,
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

    // Утка
    Meat(
        id = "duck",
        titleRes = R.string.duck,
        imageRes = R.drawable.duck,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(337, 28.0, 19.0, 0.0),
        timeForFood = TimeForFood.DINNER
    ),

    // Гусь
    Meat(
        id = "goose",
        titleRes = R.string.goose,
        imageRes = R.drawable.chicken,
        foodType = FoodType.MEAT,
        relatedReceipts = emptyList(),
        vitamins = listOf(
            vitaminsMap["vitamin_b3"]!!,
            vitaminsMap["vitamin_b6"]!!,
            vitaminsMap["vitamin_b12"]!!
        ),
        minerals = listOf(
            mineralsMap["selenium"]!!,
            mineralsMap["zinc"]!!,
            mineralsMap["iron"]!!
        ),
        fatKisloty = emptyList(),
        kpfc_100g = Kpfc(371, 33.0, 18.0, 0.0),
        timeForFood = TimeForFood.DINNER
    )
)

val allFood = allFruits + allBerries + allNuts + allVegetables + allMeat