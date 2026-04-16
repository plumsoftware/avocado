package ru.plumsoftware.avocado.ui.screen

import kotlinx.serialization.Serializable

object AppDestination {

    @Serializable
    data class MainScreen (
        val instaOpenMealPlanner: Boolean
    )

    @Serializable
    data class DetailedScreen(
        val foodId: String
    )

    @Serializable
    data object Onboarding

    @Serializable
    data object Favorite

    @Serializable
    data class ReceiptDetailRoute(val receiptId: String)

    @Serializable
    data object PrivacyPolicy

    @Serializable
    data object ShoppingList

    @Serializable
    data object Scanner
}