package ru.plumsoftware.avocado.data.base.model.receipt

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TypicalReceipt(
    override val id: String,
    @StringRes override val titleRes: Int,
    @StringRes override val descRes: Int, // Краткое описание (например, "Легкий салат...")
    @StringRes override val receiptText: Int, // Полный текст
    @DrawableRes override val imageRes: Int,
    override val relatedFood: List<String>, // ID продуктов (например "avocado", "tomato")
    override val timeMinutes: Int, // Время готовки
    override val calories: Int,
    override val difficulty: Int = 1, // 1 - легко, 2 - средне, 3 - сложно
    val category: String
) : Receipt // Твой интерфейс
