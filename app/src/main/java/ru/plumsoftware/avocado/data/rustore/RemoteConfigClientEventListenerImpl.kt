package ru.plumsoftware.avocado.data.rustore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.plumsoftware.avocado.App
import ru.plumsoftware.avocado.BuildConfig
import ru.plumsoftware.avocado.data.season_products.SeasonProductsRepository
import ru.plumsoftware.avocado.ui.log
import ru.rustore.sdk.remoteconfig.RemoteConfigClientEventListener
import ru.rustore.sdk.remoteconfig.RemoteConfigException

class RemoteConfigClientEventListenerImpl(private val seasonProductsRepository: SeasonProductsRepository) : RemoteConfigClientEventListener {

    // Создаем скоуп для фонового сохранения
    private val scope = CoroutineScope(Dispatchers.IO)

    // Настраиваем JSON парсер (чтобы не падал, если придут лишние поля)
    private val json = Json { ignoreUnknownKeys = true }

    override fun backgroundJobErrors(exception: RemoteConfigException.BackgroundConfigUpdateError) {
        //Возвращает ошибку фоновой работы
    }

    override fun firstLoadComplete() {
        //Вызывается при окончании первой загрузки
    }

    override fun initComplete() {
        if (BuildConfig.DEBUG)
            App.remoteConfigClient.getRemoteConfig().addOnSuccessListener { remoteConfig ->
                if (remoteConfig.containsKey("test")) {
                    log(remoteConfig.getString("test"))
                }
            }

        App.remoteConfigClient.getRemoteConfig().addOnSuccessListener { remoteConfig ->
            // Проверяем, есть ли наш конфиг
            if (remoteConfig.containsKey("season_products")) {
                try {
                    val jsonString = remoteConfig.getString("season_products")

                    log("Received season products: $jsonString")
                    // Парсим JSON в наш Data Class
                    val seasonData = json.decodeFromString<SeasonProductsResponse>(jsonString)

                    // Сохраняем в DataStore
                    scope.launch {
                        seasonProductsRepository.addSeasonProducts(seasonData)
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // Ошибка парсинга
                }
            }
        }
    }

    override fun memoryCacheUpdated() {
        //Вызывается при измененияя кэша в памяти
    }

    override fun persistentStorageUpdated() {
        //Вызывается при изменении постоянного хранилища
    }

    override fun remoteConfigNetworkRequestFailure(throwable: Throwable) {
        //Вызывается при ошибке сетевого запроса Remote Сonfig
    }
}