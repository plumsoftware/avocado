package ru.plumsoftware.avocado.data.rustore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonProductsResponse(
    @SerialName("date_start") val dateStart: String,
    @SerialName("date_end") val dateEnd: String,
    @SerialName("products") val products: Array<String>,
    @SerialName("title") val title: String,
    @SerialName("promo") val promo: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SeasonProductsResponse

        if (dateStart != other.dateStart) return false
        if (dateEnd != other.dateEnd) return false
        if (!products.contentEquals(other.products)) return false
        if (title != other.title) return false
        if (promo != other.promo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateStart.hashCode()
        result = 31 * result + dateEnd.hashCode()
        result = 31 * result + products.contentHashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + promo.hashCode()
        return result
    }
}