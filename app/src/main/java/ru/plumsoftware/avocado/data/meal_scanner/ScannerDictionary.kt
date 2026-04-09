package ru.plumsoftware.avocado.data.meal_scanner

import ru.plumsoftware.avocado.R

object ScannerDictionary {
    private val foodSynonyms = mapOf(
        "apple" to listOf("apple", "granny smith", "macintosh"),
        "banana" to listOf("banana"),
        "orange" to listOf("orange", "citrus", "tangerine", "mandarin", "grapefruit"),
        "lemon" to listOf("lemon", "lime"),
        "tomato" to listOf("tomato", "bell pepper"),
        "cucumber" to listOf("cucumber", "zucchini", "squash"),
        "carrot" to listOf("carrot"),
        "broccoli" to listOf("broccoli", "cauliflower"),
        "chicken_breast" to listOf("meat", "poultry", "chicken"),
        "beef_lean" to listOf("beef", "steak", "red meat"),
        "salmon" to listOf("fish", "salmon", "seafood", "trout"),
        "shrimp" to listOf("shrimp", "prawn"),
        "champignon" to listOf("mushroom", "fungus", "agaric"),
        "walnut" to listOf("nut", "walnut", "almond", "pecan"),
        "strawberry" to listOf("strawberry", "berry"),
        "grape" to listOf("grape")
    )

    fun findFoodId(mlKitLabel: String): String? {
        val label = mlKitLabel.lowercase()
        for ((foodId, synonyms) in foodSynonyms) {
            // Ищем совпадения
            if (synonyms.any { label.contains(it) }) return foodId
        }
        return null
    }

    fun getLocalizedName(foodId: String): Int {
        return when (foodId) {
            "apple" -> R.string.apple
            "banana" -> R.string.banana
            "orange" -> R.string.orange
            "mandarin" -> R.string.mandarin
            "lemon" -> R.string.lemon
            "tomato" -> R.string.tomato
            "cucumber" -> R.string.cucumber
            "carrot" -> R.string.carrot
            "broccoli" -> R.string.broccoli
            "chicken_breast" -> R.string.chicken_breast
            "beef_lean" -> R.string.beef_lean
            "salmon" -> R.string.salmon
            "shrimp" -> R.string.shrimp
            "champignon" -> R.string.champignon
            "walnut" -> R.string.walnut
            "strawberry" -> R.string.strawberry
            "grape" -> R.string.grape
            else -> R.string.app_name
        }
    }
}
