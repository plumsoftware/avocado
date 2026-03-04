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
    )
)

fun getReceiptsByIds(ids: List<String>): List<TypicalReceipt> {
    return allReceipts.filter { it.id in ids }
}