package ru.plumsoftware.avocado.data.base.model.metrika

import ru.plumsoftware.avocado.BuildConfig

object YaMetrikaConfig {
    /**
     * 3 - Google Play
     * 2 - Huawei App Gallery
     * 1 - RuStore
     */
    val AppIdKey = if (BuildConfig.PLATFORM == 1) "6a8e1db4-fa54-4ec3-9138-d70b8f84b48f" else if (BuildConfig.PLATFORM == 2) "" else if (BuildConfig.PLATFORM == 3) "e5717775-0199-42b3-a045-2b31765db0d3" else ""
}