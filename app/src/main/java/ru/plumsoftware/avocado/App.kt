package ru.plumsoftware.avocado

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import ru.plumsoftware.avocado.data.base.model.metrika.YaMetrikaConfig
import ru.plumsoftware.avocado.data.rustore.RuStoreConfig
import ru.plumsoftware.avocado.data.rustore.LanguageParameterProvider
import ru.plumsoftware.avocado.data.rustore.RemoteConfigClientEventListenerImpl
import ru.plumsoftware.avocado.data.season_products.SeasonProductsRepository
import ru.plumsoftware.avocado.data.season_products.util.seasonProductsStore
import ru.plumsoftware.avocado.data.user_preferences.util.userPreferencesDataStore
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.pushclient.RuStorePushClient
import ru.rustore.sdk.pushclient.common.logger.DefaultLogger
import ru.rustore.sdk.pushclient.utils.resolveForPush
import ru.rustore.sdk.remoteconfig.AppId
import ru.rustore.sdk.remoteconfig.AppVersion
import ru.rustore.sdk.remoteconfig.DeviceModel
import ru.rustore.sdk.remoteconfig.OsVersion
import ru.rustore.sdk.remoteconfig.RemoteConfigClient
import ru.rustore.sdk.remoteconfig.RemoteConfigClientBuilder
import ru.rustore.sdk.remoteconfig.UpdateBehaviour

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initYaMetrica()

        Companion.seasonProductsRepository = SeasonProductsRepository(this.seasonProductsStore)
        val listener = RemoteConfigClientEventListenerImpl(seasonProductsRepository = seasonProductsRepository)

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
            .setUpdateBehaviour(UpdateBehaviour.Actual)
            .build()

        Companion.remoteConfigClient = remoteConfigClient

        remoteConfigClient.init()

        initRuStorePushClient()
    }

    private fun initYaMetrica() {
        val config = AppMetricaConfig.newConfigBuilder(YaMetrikaConfig.AppIdKey).build()
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config)
    }

    private fun initRuStorePushClient() {
        RuStorePushClient.init(
            application = this,
            projectId = RuStoreConfig.Push.PUSH_PROJECT_ID,
            logger = DefaultLogger("rustore-push-log")
        )

        RuStorePushClient.checkPushAvailability()
            .addOnSuccessListener { result ->
                when (result) {
                    FeatureAvailabilityResult.Available -> {
                        // Process push available
                    }

                    is FeatureAvailabilityResult.Unavailable -> {
                        result.cause.resolveForPush(this)
                    }
                }
            }
            .addOnFailureListener { throwable ->
                // Process error
            }
    }

    companion object {
        lateinit var remoteConfigClient: RemoteConfigClient
        lateinit var seasonProductsRepository: SeasonProductsRepository
        var actualVersionCode: Int = -1
    }
}