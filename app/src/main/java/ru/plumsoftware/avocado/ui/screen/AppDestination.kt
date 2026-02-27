package ru.plumsoftware.avocado.ui.screen

import kotlinx.serialization.Serializable

object AppDestination {

    @Serializable
    data object MainScreen

    @Serializable
    data class DetailedScreen(
        val foodId: String
    )

    @Serializable
    data object Onboarding
}