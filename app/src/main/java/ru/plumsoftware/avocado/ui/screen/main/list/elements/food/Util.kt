package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.TimeForFood
import ru.plumsoftware.avocado.data.base.model.food.allBerries
import ru.plumsoftware.avocado.data.base.model.food.allFruits
import ru.plumsoftware.avocado.data.base.model.food.allMeat
import ru.plumsoftware.avocado.data.base.model.food.allNuts
import ru.plumsoftware.avocado.data.base.model.food.allVegetables
import kotlin.collections.flatten

val recomendedOnBreakfast =
    allFruits.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(3) +
            allBerries.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(2) +
            allNuts.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(2)

val withFiber =
    allVegetables.filter { it.kpfc_100g.fiber >= 2.5 }.take(6)

val top7HighProteinFoods = listOf(
    allMeat.first { it.id == "chicken_breast" },      // 31г
    allMeat.first { it.id == "turkey_breast" },       // 30г
    allMeat.first { it.id == "beef_lean" },           // 26г
    allMeat.first { it.id == "rabbit" },              // 26г
    allMeat.first { it.id == "chicken_thigh" },       // 26г
    allMeat.first { it.id == "pork_tenderloin" },     // 26г
    allMeat.first { it.id == "veal" }                 // 25г
)