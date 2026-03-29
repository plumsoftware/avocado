package ru.plumsoftware.avocado.data.season_products.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import ru.plumsoftware.avocado.data.season_products.SeasonProductsSerializer
import ru.plumsoftware.avocado.snippets.proto.SeasonProducts

val Context.seasonProductsStore: DataStore<SeasonProducts> by dataStore(
    fileName = "season_products.proto.pb",
    serializer = SeasonProductsSerializer
)