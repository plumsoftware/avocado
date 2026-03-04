package ru.plumsoftware.avocado.data.base.model.receipt

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface Receipt {
    val id: String
    @get:StringRes val titleRes: Int
    @get:StringRes val descRes: Int
    @get:StringRes val receiptText: Int
    @get:DrawableRes val imageRes: Int
    val relatedFood: List<String>
    val timeMinutes: Int
    val calories: Int
    val difficulty: Int
}