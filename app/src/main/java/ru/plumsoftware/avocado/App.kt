package ru.plumsoftware.avocado

import android.app.Application
import ru.plumsoftware.avocado.data.ads.RuStoreConfig
import ru.plumsoftware.avocado.data.rustore.LanguageParameterProvider
import ru.plumsoftware.avocado.data.rustore.RemoteConfigClientEventListenerImpl
import ru.rustore.sdk.remoteconfig.AppId
import ru.rustore.sdk.remoteconfig.AppVersion
import ru.rustore.sdk.remoteconfig.DeviceModel
import ru.rustore.sdk.remoteconfig.OsVersion
import ru.rustore.sdk.remoteconfig.RemoteConfigClient
import ru.rustore.sdk.remoteconfig.RemoteConfigClientBuilder

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val listener = RemoteConfigClientEventListenerImpl()

        val remoteConfigClient = RemoteConfigClientBuilder(
            appId = AppId(RuStoreConfig.APP_ID),
            context = applicationContext
        )
            .setOsVersion(
                OsVersion("${android.os.Build.VERSION.RELEASE} (${android.os.Build.VERSION.SDK_INT})")
            )
            .setDevice(
                DeviceModel(
                    "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}"
                )
            )
            .setAppVersion(
                AppVersion(BuildConfig.VERSION_NAME)
            )
            .setConfigRequestParameterProvider(LanguageParameterProvider())
            .setRemoteConfigClientEventListener(listener)
            .build()

        Companion.remoteConfigClient = remoteConfigClient

        remoteConfigClient.init()
    }

    companion object {
        lateinit var remoteConfigClient: RemoteConfigClient
    }
}