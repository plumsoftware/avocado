package ru.plumsoftware.avocado.data.season_products

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.plumsoftware.avocado.snippets.proto.SeasonProducts
import java.io.InputStream
import java.io.OutputStream

object SeasonProductsSerializer : Serializer<SeasonProducts> {
    override val defaultValue: SeasonProducts = SeasonProducts.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SeasonProducts {
        try {
            return SeasonProducts.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SeasonProducts,
        output: OutputStream
    ) = t.writeTo(output)
}