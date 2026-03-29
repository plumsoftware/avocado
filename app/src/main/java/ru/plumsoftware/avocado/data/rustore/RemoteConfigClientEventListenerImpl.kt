package ru.plumsoftware.avocado.data.rustore

import ru.plumsoftware.avocado.App
import ru.plumsoftware.avocado.BuildConfig
import ru.plumsoftware.avocado.ui.log
import ru.rustore.sdk.remoteconfig.RemoteConfigClientEventListener
import ru.rustore.sdk.remoteconfig.RemoteConfigException

class RemoteConfigClientEventListenerImpl : RemoteConfigClientEventListener {
    override fun backgroundJobErrors(exception: RemoteConfigException.BackgroundConfigUpdateError) {
        //Возвращает ошибку фоновой работы
    }

    override fun firstLoadComplete() {
        //Вызывается при окончании первой загрузки
    }

    override fun initComplete() {
        //Вызывается при окончании инциализации
        if (BuildConfig.DEBUG)
            App.remoteConfigClient.getRemoteConfig().addOnSuccessListener { remoteConfig ->
                if (remoteConfig.containsKey("test")) {
                    log(remoteConfig.getString("test"))
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