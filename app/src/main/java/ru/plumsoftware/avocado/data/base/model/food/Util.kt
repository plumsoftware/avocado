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