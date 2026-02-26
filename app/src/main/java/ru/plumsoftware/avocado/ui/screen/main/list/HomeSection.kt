package ru.plumsoftware.avocado.ui.screen.main.list

import ru.plumsoftware.avocado.data.base.model.food.Food

data class HomeSection(
    val titleRes: Int,
    val items: List<Food>
)