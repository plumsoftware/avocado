package ru.plumsoftware.avocado.data.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(private val dao: FavoriteDao) {

    // Преобразуем List в Set для быстрой проверки contains()
    val favoriteIds: Flow<Set<String>> = dao.getFavoriteIds()
        .map { it.toSet() }

    suspend fun toggleFavorite(foodId: String) {
        if (dao.isFavorite(foodId)) {
            dao.removeFromFavorites(foodId)
        } else {
            dao.addToFavorites(FavoriteEntity(foodId))
        }
    }
}