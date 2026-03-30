package ru.plumsoftware.avocado.data.season_products

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.plumsoftware.avocado.data.rustore.SeasonProductsResponse
import ru.plumsoftware.avocado.snippets.proto.SeasonProducts

class SeasonProductsRepository(
    private val dataStore: DataStore<SeasonProducts>
) {
    val seasonProducts: Flow<SeasonProductsResponse> = dataStore.data.map { preferences ->
        SeasonProductsResponse(
            dateStart = preferences.dateStart,
            dateEnd = preferences.dateEnd,
            products = preferences.productsList.toTypedArray(),
            title = preferences.title,
            promo = preferences.promo
        )
    }

    suspend fun addSeasonProducts(seasonProducts: SeasonProductsResponse) {
        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setDateStart(seasonProducts.dateStart)
                .setDateEnd(seasonProducts.dateEnd)
                .setPromo(seasonProducts.promo)
                .setTitle(seasonProducts.title)
                .clearProducts()
                .addAllProducts(seasonProducts.products.toList())
                .build()
        }
    }
}