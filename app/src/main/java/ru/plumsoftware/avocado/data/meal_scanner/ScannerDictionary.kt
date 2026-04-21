package ru.plumsoftware.avocado.data.meal_scanner

import ru.plumsoftware.avocado.R

object ScannerDictionary {
    // Ключ - ID продукта из базы. Значение - список слов (меток), которые может выдать нейросеть TFLite
    private val foodSynonyms = mapOf(
        // === ФРУКТЫ ===
        "banana" to listOf("banana"),
        "apple" to listOf("apple", "granny smith", "macintosh"),
        "orange" to listOf("orange", "citrus", "tangerine", "clementine"),
        "kiwi" to listOf("kiwi", "kiwifruit"),
        "pear" to listOf("pear"),
        "lemon" to listOf("lemon", "lime"),
        "peach" to listOf("peach"),
        "apricot" to listOf("apricot"),
        "mandarin" to listOf("mandarin", "tangerine"),
        "pineapple" to listOf("pineapple"),
        "pomegranate" to listOf("pomegranate"),
        "mango" to listOf("mango"),
        "persimmon" to listOf("persimmon"),

        // === ЯГОДЫ ===
        "strawberry" to listOf("strawberry"),
        "blueberry" to listOf("blueberry", "bilberry"),
        "watermelon" to listOf("watermelon"),
        "grape" to listOf("grape", "vine"),
        "avocado" to listOf("avocado", "guacamole"),
        "raspberry" to listOf("raspberry"),
        "blackberry" to listOf("blackberry"),
        "currant" to listOf("currant", "gooseberry"),
        "cranberry" to listOf("cranberry"),
        "cherry" to listOf("cherry"),

        // === ОРЕХИ ===
        "almond" to listOf("almond"),
        "hazelnut" to listOf("hazelnut", "filbert"),
        "cashew" to listOf("cashew"),
        "peanut" to listOf("peanut"),
        "pistachio" to listOf("pistachio"),
        "pine_nut" to listOf("pine nut", "pignoli"),
        "pecan" to listOf("pecan"),
        "macadamia" to listOf("macadamia"),
        "chestnut" to listOf("chestnut"),
        "walnut" to listOf("walnut", "nut", "mixed nuts"), // nut - как запасной вариант

        // === ОВОЩИ ===
        "potato" to listOf("potato"),
        "carrot" to listOf("carrot"),
        "onion" to listOf("onion", "shallot"),
        "garlic" to listOf("garlic"),
        "tomato" to listOf("tomato"),
        "cucumber" to listOf("cucumber"),
        "cabbage" to listOf("cabbage", "brussels sprout"),
        "broccoli" to listOf("broccoli", "cauliflower"),
        "bell_pepper" to listOf("bell pepper", "pepper", "capsicum"),
        "beet" to listOf("beet", "beetroot"),
        "pumpkin" to listOf("pumpkin", "squash", "gourd"),
        "zucchini" to listOf("zucchini", "courgette"),
        "eggplant" to listOf("eggplant", "aubergine"),
        "radish" to listOf("radish"),
        "spinach" to listOf("spinach", "leaf vegetable"),

        // === МЯСО И ПТИЦА ===
        "chicken_thigh" to listOf("chicken wing", "chicken leg"),
        "turkey_breast" to listOf("turkey"),
        "beef_ribeye" to listOf("steak", "ribeye"),
        "pork_tenderloin" to listOf("pork", "pig", "ham"),
        "pork_neck" to listOf("pork neck", "pork chop"),
        "lamb" to listOf("lamb", "mutton"),
        "veal" to listOf("veal", "calf"),
        "rabbit" to listOf("rabbit", "hare"),
        "duck" to listOf("duck"),
        "goose" to listOf("goose"),
        "beef_lean" to listOf("beef", "red meat", "meat"), // meat - как запасной вариант
        "chicken_breast" to listOf("chicken", "poultry", "hen", "broiler"), // poultry - как запасной

        // === РЫБА И МОРЕПРОДУКТЫ ===
        "mackerel" to listOf("mackerel"),
        "herring" to listOf("herring"),
        "tuna" to listOf("tuna"),
        "trout" to listOf("trout"),
        "sardine" to listOf("sardine", "pilchard"),
        "cod" to listOf("cod"),
        "pollock" to listOf("pollock", "pollack"),
        "shrimp" to listOf("shrimp", "prawn", "shellfish"),
        "salmon" to listOf("salmon", "fish", "seafood"), // fish - как запасной вариант

        // === ГРИБЫ ===
        "white_mushroom" to listOf("porcini", "bolete", "penny bun"),
        "chanterelle" to listOf("chanterelle"),
        "oyster_mushroom" to listOf("oyster mushroom"),
        "shiitake" to listOf("shiitake"),
        "champignon" to listOf("champignon", "agaric", "mushroom", "fungus") // mushroom - как запасной вариант
    )

    fun findFoodId(tfLabel: String): String? {
        val label = tfLabel.lowercase()
        for ((foodId, synonyms) in foodSynonyms) {
            // Ищем совпадения (если нейросеть выдала слово, которое есть у нас в синонимах)
            if (synonyms.any { label.contains(it) }) return foodId
        }
        return null
    }

    fun getLocalizedName(foodId: String): Int {
        return when (foodId) {
            // Фрукты
            "banana" -> R.string.banana
            "apple" -> R.string.apple
            "orange" -> R.string.orange
            "kiwi" -> R.string.kiwi
            "pear" -> R.string.pear
            "lemon" -> R.string.lemon
            "peach" -> R.string.peach
            "apricot" -> R.string.apricot
            "mandarin" -> R.string.mandarin
            "pineapple" -> R.string.pineapple
            "pomegranate" -> R.string.pomegranate
            "mango" -> R.string.mango
            "persimmon" -> R.string.persimmon

            // Ягоды
            "strawberry" -> R.string.strawberry
            "blueberry" -> R.string.blueberry
            "watermelon" -> R.string.watermelon
            "grape" -> R.string.grape
            "avocado" -> R.string.avocado
            "raspberry" -> R.string.raspberry
            "blackberry" -> R.string.blackberry
            "currant" -> R.string.currant
            "cranberry" -> R.string.cranberry
            "cherry" -> R.string.cherry

            // Орехи
            "walnut" -> R.string.walnut
            "almond" -> R.string.almond
            "hazelnut" -> R.string.hazelnut
            "cashew" -> R.string.cashew
            "peanut" -> R.string.peanut
            "pistachio" -> R.string.pistachio
            "pine_nut" -> R.string.pine_nut
            "pecan" -> R.string.pecan
            "macadamia" -> R.string.macadamia
            "chestnut" -> R.string.chestnut

            // Овощи
            "potato" -> R.string.potato
            "carrot" -> R.string.carrot
            "onion" -> R.string.onion
            "garlic" -> R.string.garlic
            "tomato" -> R.string.tomato
            "cucumber" -> R.string.cucumber
            "cabbage" -> R.string.cabbage
            "broccoli" -> R.string.broccoli
            "bell_pepper" -> R.string.bell_pepper
            "beet" -> R.string.beet
            "pumpkin" -> R.string.pumpkin
            "zucchini" -> R.string.zucchini
            "eggplant" -> R.string.eggplant
            "radish" -> R.string.radish
            "spinach" -> R.string.spinach

            // Мясо
            "chicken_breast" -> R.string.chicken_breast
            "chicken_thigh" -> R.string.chicken_thigh
            "turkey_breast" -> R.string.turkey_breast
            "beef_lean" -> R.string.beef_lean
            "beef_ribeye" -> R.string.beef_ribeye
            "pork_tenderloin" -> R.string.pork_tenderloin
            "pork_neck" -> R.string.pork_neck
            "lamb" -> R.string.lamb
            "veal" -> R.string.veal
            "rabbit" -> R.string.rabbit
            "duck" -> R.string.duck
            "goose" -> R.string.goose

            // Рыба
            "salmon" -> R.string.salmon
            "mackerel" -> R.string.mackerel
            "herring" -> R.string.herring
            "tuna" -> R.string.tuna
            "trout" -> R.string.trout
            "sardine" -> R.string.sardine
            "cod" -> R.string.cod
            "pollock" -> R.string.pollock
            "shrimp" -> R.string.shrimp

            // Грибы
            "champignon" -> R.string.champignon
            "white_mushroom" -> R.string.white_mushroom
            "chanterelle" -> R.string.chanterelle
            "oyster_mushroom" -> R.string.oyster_mushroom
            "shiitake" -> R.string.shiitake

            // На случай непредвиденных ошибок возвращаем название приложения
            else -> R.string.app_name
        }
    }
}
