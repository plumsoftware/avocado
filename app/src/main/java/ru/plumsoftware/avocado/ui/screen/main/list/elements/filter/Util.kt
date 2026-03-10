package ru.plumsoftware.avocado.ui.screen.main.list.elements.filter

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.FoodType

val filters = listOf(
    Filter(
        id = 1,
        title = R.string.fruits,
        icon = R.drawable.fruit_icon,
        isSelected = false,
        foodType = FoodType.FRUIT
    ),
    Filter(
        id = 2,
        title = R.string.vegetables,
        icon = R.drawable.vegetable_icon,
        foodType = FoodType.VEGETABLE
    ),
    Filter(
        id = 3,
        title = R.string.berries,
        icon = R.drawable.strawberry_icon,
        isSelected = false,
        foodType = FoodType.STRAWBERRY
    ),
    Filter(
        id = 4,
        title = R.string.meat,
        icon = R.drawable.meat_icon,
        foodType = FoodType.MEAT
    ),
    Filter(
        id = 5,
        title = R.string.fish,
        icon = R.drawable.fish_icon,
        foodType = FoodType.FISH
    ),
//    Filter(
//        id = 6,
//        title = R.string.green,
//        icon = R.drawable.green_icon,
//        foodType = FoodType.GREEN
//    ),
    Filter(
        id = 7,
        title = R.string.nut,
        icon = R.drawable.nut_icon,
        foodType = FoodType.NUT
    ),
    Filter(
        id = 8,
        title = R.string.mushrooms,
        icon = R.drawable.mushroom_icon,
        foodType = FoodType.MUSHROOM
    ),
//    Filter(
//        id = 9,
//        title = R.string.seafood,
//        icon = R.drawable.seafood_icon,
//        foodType = FoodType.SEAFOOD
//    )
)