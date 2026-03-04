package ru.plumsoftware.avocado.data.base.model.receipt

import ru.plumsoftware.avocado.R

// Внутри RecipesViewModel

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
        category = "Завтрак"
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
        category = "Салаты"
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
        category = "Смузи"
    ),

    // 4. Курица с брокколи
    TypicalReceipt(
        id = "chicken_broccoli",
        titleRes = R.string.rec_chicken_broccoli,
        descRes = R.string.rec_desc_chicken_broccoli,
        receiptText = R.string.rec_text_chicken_broccoli,
        imageRes = R.drawable.chicken_2, // Используем картинку курицы или добавь свою
        relatedFood = listOf("chicken_breast", "broccoli", "garlic"),
        timeMinutes = 20,
        calories = 280,
        difficulty = 1,
        category = "Ужин"
    ),

    // 5. Лосось
    TypicalReceipt(
        id = "baked_salmon",
        titleRes = R.string.rec_baked_salmon,
        descRes = R.string.rec_desc_baked_salmon,
        receiptText = R.string.rec_text_baked_salmon,
        imageRes = R.drawable.salmon, // Картинка лосося
        relatedFood = listOf("salmon", "lemon"),
        timeMinutes = 25,
        calories = 380,
        difficulty = 1,
        category = "Ужин"
    ),

    // 6. Рататуй
    TypicalReceipt(
        id = "ratatouille",
        titleRes = R.string.rec_ratatouille,
        descRes = R.string.rec_desc_ratatouille,
        receiptText = R.string.rec_text_ratatouille,
        imageRes = R.drawable.tomato, // Временно томат, лучше найти картинку рататуя
        relatedFood = listOf("eggplant", "zucchini", "tomato", "bell_pepper", "garlic"),
        timeMinutes = 50,
        calories = 150,
        difficulty = 2,
        category = "Обед"
    ),

    // 7. Фруктовый салат
    TypicalReceipt(
        id = "fruit_salad",
        titleRes = R.string.rec_fruit_salad,
        descRes = R.string.rec_desc_fruit_salad,
        receiptText = R.string.rec_text_fruit_salad,
        imageRes = R.drawable.apple, // Временно яблоко
        relatedFood = listOf("banana", "apple", "kiwi", "mandarin", "yogurt"), // yogurt если есть, или убери
        timeMinutes = 10,
        calories = 120,
        difficulty = 1,
        category = "Завтрак"
    )
)

fun getReceiptsByIds(ids: List<String>): List<TypicalReceipt> {
    return allReceipts.filter { it.id in ids }
}