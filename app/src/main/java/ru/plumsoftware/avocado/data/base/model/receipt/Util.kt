package ru.plumsoftware.avocado.data.base.model.receipt

import ru.plumsoftware.avocado.R

val allReceipts = listOf(
    // 1. Тост с авокадо
    TypicalReceipt(
        id = "avocado_toast",
        titleRes = R.string.rec_avocado_toast,
        descRes = R.string.rec_desc_avocado_toast,
        receiptText = R.string.rec_text_avocado_toast,
        imageRes = R.drawable.avocado_toast,
        relatedFood = listOf("avocado", "bread", "egg"),
        timeMinutes = 10,
        calories = 350,
        difficulty = 1,
        category = RecipeCategory.BREAKFAST // <-- Было "Завтрак"
    ),

    // 2. Греческий салат
    TypicalReceipt(
        id = "greek_salad",
        titleRes = R.string.rec_greek_salad,
        descRes = R.string.rec_desc_greek_salad,
        receiptText = R.string.rec_text_greek_salad,
        imageRes = R.drawable.greek_salad,
        relatedFood = listOf("tomato", "cucumber", "cheese", "olive"),
        timeMinutes = 15,
        calories = 250,
        difficulty = 1,
        category = RecipeCategory.SALAD // <-- Было "Салаты"
    ),

    // 3. Смузи
    TypicalReceipt(
        id = "green_smoothie",
        titleRes = R.string.rec_green_smoothie,
        descRes = R.string.rec_desc_green_smoothie,
        receiptText = R.string.rec_text_green_smoothie,
        imageRes = R.drawable.green_smoothie,
        relatedFood = listOf("spinach", "apple", "kiwi", "banana"),
        timeMinutes = 5,
        calories = 180,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    ),

    // 4. Курица с брокколи
    TypicalReceipt(
        id = "chicken_broccoli",
        titleRes = R.string.rec_chicken_broccoli,
        descRes = R.string.rec_desc_chicken_broccoli,
        receiptText = R.string.rec_text_chicken_broccoli,
        imageRes = R.drawable.chicken_broccoli,
        relatedFood = listOf("chicken_breast", "broccoli", "garlic"),
        timeMinutes = 20,
        calories = 280,
        difficulty = 1,
        category = RecipeCategory.DINNER // <-- Было "Ужин"
    ),

    // 5. Лосось
    TypicalReceipt(
        id = "baked_salmon",
        titleRes = R.string.rec_baked_salmon,
        descRes = R.string.rec_desc_baked_salmon,
        receiptText = R.string.rec_text_baked_salmon,
        imageRes = R.drawable.baked_salmon,
        relatedFood = listOf("salmon", "lemon"),
        timeMinutes = 25,
        calories = 380,
        difficulty = 1,
        category = RecipeCategory.DINNER // Оставляем DINNER, или можно LUNCH
    ),

    // 6. Рататуй
    TypicalReceipt(
        id = "ratatouille",
        titleRes = R.string.rec_ratatouille,
        descRes = R.string.rec_desc_ratatouille,
        receiptText = R.string.rec_text_ratatouille,
        imageRes = R.drawable.ratatouille,
        relatedFood = listOf("eggplant", "zucchini", "tomato", "bell_pepper", "garlic"),
        timeMinutes = 50,
        calories = 150,
        difficulty = 2,
        category = RecipeCategory.LUNCH // <-- Было "Обед"
    ),

    // 7. Фруктовый салат
    TypicalReceipt(
        id = "fruit_salad",
        titleRes = R.string.rec_fruit_salad,
        descRes = R.string.rec_desc_fruit_salad,
        receiptText = R.string.rec_text_fruit_salad,
        imageRes = R.drawable.fruit_salad,
        relatedFood = listOf("banana", "apple", "kiwi", "mandarin", "yogurt"),
        timeMinutes = 10,
        calories = 120,
        difficulty = 1,
        category = RecipeCategory.SNACK // <-- Новая категория!
    ),

    // 8. Ягодный
    TypicalReceipt(
        id = "berry_blast",
        titleRes = R.string.rec_berry_blast,
        descRes = R.string.rec_desc_berry_blast,
        receiptText = R.string.rec_text_berry_blast,
        imageRes = R.drawable.berry_blast,
        relatedFood = listOf("strawberry", "raspberry", "blueberry", "blackberry"),
        timeMinutes = 5,
        calories = 140,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    ),

    // 9. Тропический
    TypicalReceipt(
        id = "tropical_paradise",
        titleRes = R.string.rec_tropical_paradise,
        descRes = R.string.rec_desc_tropical_paradise,
        receiptText = R.string.rec_text_tropical_paradise,
        imageRes = R.drawable.tropical_paradise,
        relatedFood = listOf("mango", "pineapple", "orange"),
        timeMinutes = 7,
        calories = 190,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    ),

    // 10. Арахисовый
    TypicalReceipt(
        id = "peanut_power",
        titleRes = R.string.rec_peanut_power,
        descRes = R.string.rec_desc_peanut_power,
        receiptText = R.string.rec_text_peanut_power,
        imageRes = R.drawable.peanut_power,
        relatedFood = listOf("banana", "peanut", "almond"),
        timeMinutes = 5,
        calories = 380,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    ),

    // 11. Морковный
    TypicalReceipt(
        id = "vitamin_glow",
        titleRes = R.string.rec_vitamin_glow,
        descRes = R.string.rec_desc_vitamin_glow,
        receiptText = R.string.rec_text_vitamin_glow,
        imageRes = R.drawable.vitamin_glow,
        relatedFood = listOf("carrot", "orange", "apple"),
        timeMinutes = 8,
        calories = 110,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    ),

    // 12. Свекольный
    TypicalReceipt(
        id = "beet_detox",
        titleRes = R.string.rec_beet_detox,
        descRes = R.string.rec_desc_beet_detox,
        receiptText = R.string.rec_text_beet_detox,
        imageRes = R.drawable.beet_detox,
        relatedFood = listOf("beet", "apple", "lemon"),
        timeMinutes = 10,
        calories = 95,
        difficulty = 1,
        category = RecipeCategory.SMOOTHIE
    )
)

fun getReceiptsByIds(ids: List<String>): List<TypicalReceipt> {
    return allReceipts.filter { it.id in ids }
}