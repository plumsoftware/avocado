package ru.plumsoftware.avocado.data.base.model.healthy

data class Kpfc(
    val kals: Int,
    val fats: Double,
    val proteins: Double,
    val carbohydrates: Double,
    val fiber: Double = 0.0,
    val omega3: Double = 0.0 // грамм на 100 г
)