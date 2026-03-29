package ru.plumsoftware.avocado.data.rustore

import ru.rustore.sdk.remoteconfig.ConfigRequestParameter
import ru.rustore.sdk.remoteconfig.ConfigRequestParameterProvider
import ru.rustore.sdk.remoteconfig.Language
import java.util.Locale

class LanguageParameterProvider : ConfigRequestParameterProvider {

    override fun getConfigRequestParameter(): ConfigRequestParameter {
        val locale = Locale.getDefault()

        return ConfigRequestParameter(
            language = Language(locale.toLanguageTag())
        )
    }
}