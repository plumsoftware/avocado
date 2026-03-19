package ru.plumsoftware.avocado.data.ads

import ru.plumsoftware.avocado.BuildConfig

object AdsConfig {
    val APP_OPEN_ADS_ID: String =
        if (BuildConfig.DEBUG) "demo-appopenad-yandex" else if (BuildConfig.PLATFORM == 1) "R-M-18957454-1" else if (BuildConfig.PLATFORM == 2) "R-M-18957685-1" else "demo-appopenad-yandex"
    val INTERSTITIAL_ADS_ID: String =
        if (BuildConfig.DEBUG) "demo-interstitial-yandex" else if (BuildConfig.PLATFORM == 1) "R-M-18957454-2" else if (BuildConfig.PLATFORM == 2) "R-M-18957685-2" else "demo-interstitial-yandex"
}