package ru.plumsoftware.avocado.ui.ads

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import ru.plumsoftware.avocado.data.ads.AdsConfig

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun YandexStickyBanner(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    // Получаем ширину экрана в dp (как того требует Яндекс для адаптивного баннера)
    val screenWidth = configuration.screenWidthDp

    AndroidView(
        modifier = modifier.fillMaxWidth(), // Баннер всегда занимает всю ширину
        factory = { context ->
            BannerAdView(context).apply {
                // 1. Устанавливаем ID
                setAdUnitId(AdsConfig.BANNER_ADS_ID)

                // 2. Рассчитываем размер для Sticky (прилипающего) баннера
                setAdSize(BannerAdSize.stickySize(context, screenWidth))

                // 3. Создаем запрос и загружаем рекламу
                val adRequest = AdRequest.Builder().build()
                loadAd(adRequest)
            }
        }
    )
}