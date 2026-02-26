package ru.plumsoftware.avocado.data.onboarding

import ru.plumsoftware.avocado.R

enum class UserGoal(
    val id: String,
    val titleRes: Int,
    val iconRes: Int
) {
    LOSE_WEIGHT("lose_weight", R.string.goal_lose_weight, R.drawable.fruit_icon), // Низк. калории
    GAIN_MUSCLE("gain_muscle", R.string.goal_gain_muscle, R.drawable.muscle_icon), // Белок
    BETTER_SKIN("skin", R.string.goal_skin, R.drawable.skin_icon), // Витамин А, Е, С
    ENERGY("energy", R.string.goal_energy, R.drawable.flash_icon), // Витамин B, Углеводы
    HEART_HEALTH("heart", R.string.goal_heart, R.drawable.heart_icon), // Калий, Магний, Омега
    DIGESTION("digestion", R.string.goal_digestion, R.drawable.stomach_icon), // Клетчатка
    IMMUNITY("immunity", R.string.goal_immunity, R.drawable.shield_icon), // Витамин С, Цинк
    SLEEP("sleep", R.string.goal_sleep, R.drawable.moon_icon) // Магний
}

enum class UserRestriction(val id: String, val titleRes: Int) {
    VEGETARIAN("vegetarian", R.string.rest_vegetarian),
    VEGAN("vegan", R.string.rest_vegan),
    GLUTEN_FREE("gluten_free", R.string.rest_gluten_free),
    LACTOSE_FREE("lactose_free", R.string.rest_lactose_free),
    NUT_ALLERGY("no_nuts", R.string.rest_no_nuts),
    NO_SUGAR("no_sugar", R.string.rest_no_sugar),
    KETO("keto", R.string.rest_keto),
    LOW_CARB("low_carb", R.string.rest_low_carb),
    SEAFOOD_ALLERGY("no_seafood", R.string.rest_seafood),
    NO_EGGS("no_eggs", R.string.rest_eggs)
}