package ru.plumsoftware.avocado.data.ads

import ru.plumsoftware.avocado.BuildConfig

object AdsConfig {

    /**
     * 3 - Google Play
     * 2 - Huawei App Gallery
     * 1 - RuStore
     */
    val APP_OPEN_ADS_ID: String =
        if (BuildConfig.DEBUG) "demo-appopenad-yandex" else if (BuildConfig.PLATFORM == 1) "R-M-18957454-1" else if (BuildConfig.PLATFORM == 2) "R-M-18957685-1" else if (BuildConfig.PLATFORM == 3) "R-M-19002616-2" else "demo-appopenad-yandex"
    val INTERSTITIAL_ADS_ID: String =
        if (BuildConfig.DEBUG) "demo-interstitial-yandex" else if (BuildConfig.PLATFORM == 1) "R-M-18957454-2" else if (BuildConfig.PLATFORM == 2) "R-M-18957685-2" else if (BuildConfig.PLATFORM == 3) "R-M-19002616-1" else "demo-interstitial-yandex"


    // Храним время последнего показа в миллисекундах
    private var lastAdShownTime: Long = 0L

    // Интервал: 3 минуты (в миллисекундах)
    private const val AD_INTERVAL_MS = 3 * 60 * 1000L

    /**
     * Проверяет, прошло ли 3 минуты с момента прошлого показа.
     */
    fun canShowAd(): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - lastAdShownTime >= AD_INTERVAL_MS
    }

    /**
     * Вызывать строго в момент фактического ПОКАЗА рекламы на экране.
     */
    fun registerAdShown() {
        lastAdShownTime = System.currentTimeMillis()
    }

}