package ru.plumsoftware.avocado.data.base.model.receipt

import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.onboarding.UserGoal

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
        category = RecipeCategory.BREAKFAST,
        suitableGoals = listOf(UserGoal.HEART_HEALTH, UserGoal.ENERGY, UserGoal.BETTER_SKIN)
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
        category = RecipeCategory.SALAD,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.DIGESTION, UserGoal.BETTER_SKIN)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.IMMUNITY, UserGoal.DIGESTION)
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
        category = RecipeCategory.DINNER,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.LOSE_WEIGHT)
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
        category = RecipeCategory.DINNER,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.HEART_HEALTH, UserGoal.BETTER_SKIN)
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
        category = RecipeCategory.LUNCH,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.DIGESTION, UserGoal.HEART_HEALTH)
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
        category = RecipeCategory.SNACK,
        suitableGoals = listOf(UserGoal.ENERGY, UserGoal.IMMUNITY)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.BETTER_SKIN, UserGoal.IMMUNITY)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.ENERGY, UserGoal.IMMUNITY, UserGoal.DIGESTION)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.ENERGY)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.BETTER_SKIN, UserGoal.IMMUNITY, UserGoal.LOSE_WEIGHT)
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
        category = RecipeCategory.SMOOTHIE,
        suitableGoals = listOf(UserGoal.DIGESTION, UserGoal.HEART_HEALTH, UserGoal.LOSE_WEIGHT)
    ),

    // 13. Грибной крем-суп
    TypicalReceipt(
        id = "mushroom_soup",
        titleRes = R.string.rec_mushroom_soup,
        descRes = R.string.rec_desc_mushroom_soup,
        receiptText = R.string.rec_text_mushroom_soup,
        imageRes = R.drawable.mushroom_soup, // Замени на фото супа
        relatedFood = listOf("champignon", "potato", "onion"),
        timeMinutes = 30,
        calories = 210,
        difficulty = 2,
        category = RecipeCategory.SOUP, // ИСПОЛЬЗУЕМ КАТЕГОРИЮ СУПЫ
        suitableGoals = listOf(UserGoal.DIGESTION, UserGoal.ENERGY)
    ),

    // 14. Стейк Рибай с овощами
    TypicalReceipt(
        id = "beef_steak_veggies",
        titleRes = R.string.rec_beef_steak_veggies,
        descRes = R.string.rec_desc_beef_steak_veggies,
        receiptText = R.string.rec_text_beef_steak_veggies,
        imageRes = R.drawable.beef_steak_veggies,
        relatedFood = listOf("beef_ribeye", "tomato", "bell_pepper", "onion"),
        timeMinutes = 15,
        calories = 450,
        difficulty = 2,
        category = RecipeCategory.DINNER,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.ENERGY)
    ),

    // 15. Ореховый микс (Снек)
    TypicalReceipt(
        id = "nut_energy_mix",
        titleRes = R.string.rec_nut_energy_mix,
        descRes = R.string.rec_desc_nut_energy_mix,
        receiptText = R.string.rec_text_nut_energy_mix,
        imageRes = R.drawable.nut_energy_mix,
        relatedFood = listOf("walnut", "almond", "cashew"),
        timeMinutes = 5,
        calories = 550, // Орехи калорийные, но это полезные жиры!
        difficulty = 1,
        category = RecipeCategory.SNACK,
        suitableGoals = listOf(
            UserGoal.ENERGY,
            UserGoal.HEART_HEALTH,
            UserGoal.SLEEP
        ) // Магний в орехах улучшает сон
    ),

    // 16. Запеченные яблоки (Десерт)
    TypicalReceipt(
        id = "baked_apples",
        titleRes = R.string.rec_baked_apples,
        descRes = R.string.rec_desc_baked_apples,
        receiptText = R.string.rec_text_baked_apples,
        imageRes = R.drawable.baked_apples,
        relatedFood = listOf("apple", "walnut"),
        timeMinutes = 30,
        calories = 180,
        difficulty = 1,
        category = RecipeCategory.DESSERT,
        suitableGoals = listOf(UserGoal.DIGESTION, UserGoal.HEART_HEALTH, UserGoal.LOSE_WEIGHT)
    ),

    // 17. Нежные тефтели из индейки с кабачком
    TypicalReceipt(
        id = "turkey_meatballs",
        titleRes = R.string.rec_turkey_meatballs,
        descRes = R.string.rec_desc_turkey_meatballs,
        receiptText = R.string.rec_text_turkey_meatballs,
        imageRes = R.drawable.turkey_meatballs,
        relatedFood = listOf("turkey_breast", "zucchini", "garlic"),
        timeMinutes = 40,
        calories = 220,
        difficulty = 2,
        category = RecipeCategory.LUNCH,
        suitableGoals = listOf(
            UserGoal.GAIN_MUSCLE,
            UserGoal.LOSE_WEIGHT,
            UserGoal.DIGESTION
        ) // Отличный диетический белок
    ),

    // 18. Пышный омлет со шпинатом (Завтрак)
    TypicalReceipt(
        id = "spinach_omelet",
        titleRes = R.string.rec_spinach_omelet,
        descRes = R.string.rec_desc_spinach_omelet,
        receiptText = R.string.rec_text_spinach_omelet,
        imageRes = R.drawable.spinach_omelet,
        relatedFood = listOf("spinach", "tomato", "egg"),
        timeMinutes = 10,
        calories = 220,
        difficulty = 1,
        category = RecipeCategory.BREAKFAST,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.ENERGY, UserGoal.LOSE_WEIGHT)
    ),

    // 19. Креветки в чесночном соусе
    TypicalReceipt(
        id = "garlic_shrimp",
        titleRes = R.string.rec_garlic_shrimp,
        descRes = R.string.rec_desc_garlic_shrimp,
        receiptText = R.string.rec_text_garlic_shrimp,
        imageRes = R.drawable.garlic_shrimp,
        relatedFood = listOf("shrimp", "garlic", "lemon"),
        timeMinutes = 10,
        calories = 150,
        difficulty = 1,
        category = RecipeCategory.DINNER,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.GAIN_MUSCLE, UserGoal.HEART_HEALTH)
    ),

    // 20. Салат с тунцом (Салат / Ужин)
    TypicalReceipt(
        id = "tuna_salad",
        titleRes = R.string.rec_tuna_salad,
        descRes = R.string.rec_desc_tuna_salad,
        receiptText = R.string.rec_text_tuna_salad,
        imageRes = R.drawable.tuna_salad, // Замени на фото салата
        relatedFood = listOf("tuna", "cucumber", "tomato", "onion", "lemon"),
        timeMinutes = 5,
        calories = 210,
        difficulty = 1,
        category = RecipeCategory.SALAD,
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.GAIN_MUSCLE, UserGoal.HEART_HEALTH)
    ),

    // 21. Запеченная тыква с пеканом (Десерт / Перекус)
    TypicalReceipt(
        id = "baked_pumpkin",
        titleRes = R.string.rec_baked_pumpkin,
        descRes = R.string.rec_desc_baked_pumpkin,
        receiptText = R.string.rec_text_baked_pumpkin,
        imageRes = R.drawable.baked_pumpkin, // Замени на фото запеченной тыквы
        relatedFood = listOf("pumpkin", "pecan"),
        timeMinutes = 25,
        calories = 160,
        difficulty = 1,
        category = RecipeCategory.DESSERT, // Или SNACK
        suitableGoals = listOf(UserGoal.DIGESTION, UserGoal.BETTER_SKIN, UserGoal.IMMUNITY)
    ),

    // 22. Куриные бедра с грибами (Обед / Ужин)
    TypicalReceipt(
        id = "chicken_mushrooms",
        titleRes = R.string.rec_chicken_mushrooms,
        descRes = R.string.rec_desc_chicken_mushrooms,
        receiptText = R.string.rec_text_chicken_mushrooms,
        imageRes = R.drawable.chicken_mushrooms, // Замени на фото курицы с грибами
        relatedFood = listOf("chicken_thigh", "champignon", "onion", "garlic"),
        timeMinutes = 30,
        calories = 290,
        difficulty = 2,
        category = RecipeCategory.LUNCH,
        suitableGoals = listOf(UserGoal.GAIN_MUSCLE, UserGoal.ENERGY)
    ),

    // 23. Фаршированные перцы с индейкой (Ужин / Обед)
    TypicalReceipt(
        id = "stuffed_peppers",
        titleRes = R.string.rec_stuffed_peppers,
        descRes = R.string.rec_desc_stuffed_peppers,
        receiptText = R.string.rec_text_stuffed_peppers,
        imageRes = R.drawable.stuffed_peppers, // Замени на фото красивых фаршированных перцев
        relatedFood = listOf("bell_pepper", "turkey_breast", "onion", "tomato", "carrot"),
        timeMinutes = 50,
        calories = 240, // На порцию (очень легкое блюдо!)
        difficulty = 2,
        category = RecipeCategory.DINNER, // Можно и LUNCH
        suitableGoals = listOf(UserGoal.LOSE_WEIGHT, UserGoal.GAIN_MUSCLE, UserGoal.DIGESTION)
    ),

    // 24. Ягодное мороженое (Найс-крим) - Десерт
    TypicalReceipt(
        id = "berry_ice_cream",
        titleRes = R.string.rec_berry_ice_cream,
        descRes = R.string.rec_desc_berry_ice_cream,
        receiptText = R.string.rec_text_berry_ice_cream,
        imageRes = R.drawable.berry_ice_cream,
        relatedFood = listOf("banana", "blueberry", "strawberry", "almond"),
        timeMinutes = 10,
        calories = 140,
        difficulty = 1,
        category = RecipeCategory.DESSERT,
        suitableGoals = listOf(
            UserGoal.LOSE_WEIGHT,
            UserGoal.BETTER_SKIN,
            UserGoal.DIGESTION
        ) // Витамины для кожи, клетчатка для пищеварения
    )
)

fun getReceiptsByIds(ids: List<String>): List<TypicalReceipt> {
    return allReceipts.filter { it.id in ids }
}