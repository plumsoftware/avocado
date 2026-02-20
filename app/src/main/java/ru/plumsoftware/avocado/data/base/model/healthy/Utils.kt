package ru.plumsoftware.avocado.data.base.model.healthy

import ru.plumsoftware.avocado.R

// --- ВИТАМИНЫ ---
val popularVitamins = listOf(
    Vitamin("vitamin_a", R.string.vitamin_a, R.string.desc_vitamin_a),
    Vitamin("vitamin_c", R.string.vitamin_c, R.string.desc_vitamin_c),
    Vitamin("vitamin_d", R.string.vitamin_d, R.string.desc_vitamin_d),
    Vitamin("vitamin_e", R.string.vitamin_e, R.string.desc_vitamin_e),
    Vitamin("vitamin_k", R.string.vitamin_k, R.string.desc_vitamin_k),
    Vitamin("vitamin_b1", R.string.vitamin_b1, R.string.desc_vitamin_b1),
    Vitamin("vitamin_b2", R.string.vitamin_b2, R.string.desc_vitamin_b2),
    Vitamin("vitamin_b3", R.string.vitamin_b3, R.string.desc_vitamin_b3),
    Vitamin("vitamin_b5", R.string.vitamin_b5, R.string.desc_vitamin_b5),
    Vitamin("vitamin_b6", R.string.vitamin_b6, R.string.desc_vitamin_b6),
    Vitamin("vitamin_b7", R.string.vitamin_b7, R.string.desc_vitamin_b7),
    Vitamin("vitamin_b9", R.string.vitamin_b9, R.string.desc_vitamin_b9),
    Vitamin("vitamin_b12", R.string.vitamin_b12, R.string.desc_vitamin_b12)
)

// --- МИНЕРАЛЫ ---
val popularMinerals = listOf(
    Mineral("calcium", R.string.calcium, R.string.desc_calcium),
    Mineral("magnesium", R.string.magnesium, R.string.desc_magnesium),
    Mineral("zinc", R.string.zinc, R.string.desc_zinc),
    Mineral("iron", R.string.iron, R.string.desc_iron),
    Mineral("potassium", R.string.potassium, R.string.desc_potassium),
    Mineral("selenium", R.string.selenium, R.string.desc_selenium),
    Mineral("iodine", R.string.iodine, R.string.desc_iodine),
    Mineral("chromium", R.string.chromium, R.string.desc_chromium),
    Mineral("copper", R.string.copper, R.string.desc_copper),
    Mineral("manganese", R.string.manganese, R.string.desc_manganese),
    Mineral("phosphorus", R.string.phosphorus, R.string.desc_phosphorus)
)

// --- ЖИРНЫЕ КИСЛОТЫ ---
val fatKisloty = listOf(
    FatKisloty("omega3", R.string.omega3, R.string.desc_omega3),
    FatKisloty("coenzyme_q10", R.string.coenzyme_q10, R.string.desc_coenzyme_q10),
)

// Мапы остаются без изменений
val vitaminsMap = popularVitamins.associateBy { it.id }
val mineralsMap = popularMinerals.associateBy { it.id }
val fatsMap = fatKisloty.associateBy { it.id }