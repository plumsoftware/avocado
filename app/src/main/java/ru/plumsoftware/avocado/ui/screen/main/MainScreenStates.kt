package ru.plumsoftware.avocado.ui.screen.main

sealed class MainScreenStates() {
    data object List : MainScreenStates()
    data object Rec : MainScreenStates()
    data object Fav : MainScreenStates()
    data object Settings : MainScreenStates()

    data object Empty : MainScreenStates()
}