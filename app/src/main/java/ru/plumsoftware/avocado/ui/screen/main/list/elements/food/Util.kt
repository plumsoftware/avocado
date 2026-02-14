package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import ru.plumsoftware.avocado.data.base.model.food.TimeForFood
import ru.plumsoftware.avocado.data.base.model.food.allBerries
import ru.plumsoftware.avocado.data.base.model.food.allFruits
import ru.plumsoftware.avocado.data.base.model.food.allNuts

val recomendedOnBreakfast =
    allFruits.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(3) +
            allBerries.filter { it.timeForFood == TimeForFood.BREAKFAST }.take(3) +
            allNuts.filter { it.timeForFood == TimeForFood.BREAKFAST }