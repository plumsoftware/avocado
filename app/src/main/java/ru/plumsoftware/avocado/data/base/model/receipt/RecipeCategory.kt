package ru.plumsoftware.avocado.data.base.model.receipt

import androidx.annotation.StringRes
import ru.plumsoftware.avocado.R

enum class RecipeCategory(@StringRes val titleRes: Int) {
    ALL(R.string.cat_all),
    BREAKFAST(R.string.cat_breakfast),
    LUNCH(R.string.cat_lunch),
    DINNER(R.string.cat_dinner),
    SALAD(R.string.cat_salad),
    SMOOTHIE(R.string.cat_smoothie),
    SNACK(R.string.cat_snack),
    SOUP(R.string.cat_soup),
    DESSERT(R.string.cat_dessert)
}