package ru.plumsoftware.avocado

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import ru.plumsoftware.avocado.data.ads.AdsConfig
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.meal.MealPlanRepository
import ru.plumsoftware.avocado.data.notification.NotificationArgs
import ru.plumsoftware.avocado.data.notification.worker.DebugNotificationSender
import ru.plumsoftware.avocado.data.notification.worker.NotificationScheduler
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.data.user_preferences.util.userPreferencesDataStore
import ru.plumsoftware.avocado.data.water.WaterRepository
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.details.ProductDetailScreen
import ru.plumsoftware.avocado.ui.screen.food_scanner.FoodScannerScreen
import ru.plumsoftware.avocado.ui.screen.main.MainScreen
import ru.plumsoftware.avocado.ui.screen.main.MainViewModel
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSLoadingDialog
import ru.plumsoftware.avocado.ui.screen.main.favorite.FavoriteScreen
import ru.plumsoftware.avocado.ui.screen.main.list.ListViewModel
import ru.plumsoftware.avocado.ui.screen.main.receipt.details.ReceiptDetailScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsViewModel
import ru.plumsoftware.avocado.ui.screen.main.shopping.ShoppingScreen
import ru.plumsoftware.avocado.ui.screen.onboarding.OnboardingScreen
import ru.plumsoftware.avocado.ui.screen.privacy_policy.PrivacyPolicyScreen
import ru.plumsoftware.avocado.ui.theme.AvocadoTheme
import ru.plumsoftware.avocado.ui.theme.Dimen

class MainActivity : ComponentActivity() {
    private var hasSeenOnboardingThisSession = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Notifications
        NotificationScheduler.scheduleDailyNotification(applicationContext)

        if (BuildConfig.DEBUG) {
            DebugNotificationSender.sendTestNotifications(applicationContext)
        }

        // Variables
        val db = AvocadoDatabase.getDatabase(this)
        val favRepo = FavoritesRepository(db.favoriteDao())
        val userRepo = UserPreferencesRepository(this.userPreferencesDataStore)
        val mealPlanRepository = MealPlanRepository(db.mealPlanDao())
        val shoppingRepo = ShoppingRepository(db.shoppingDao())
        val waterRepo = WaterRepository(db.waterIntakeDao())
        val destinationRoute = intent.getStringExtra(NotificationArgs.DESTINATION_ROUTE)

        // Edge to edge
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = TRANSPARENT,
                darkScrim = TRANSPARENT
            )
        )

        // Inserts
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemGestures()
            )
            view.updatePadding(0, 0, 0, 0)
            WindowInsetsCompat.CONSUMED
        }

        // Content
        setContent {
            // Navigation
            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.Factory(repo = userRepo)
            )

            // Theme
            val themeState by settingsViewModel.currentTheme.collectAsState()
            val isDark = when (themeState) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            // MainViewModel
            val mainViewModel = ViewModelProvider(
                this,
                MainViewModel.Companion.MainViewModelFactory(userPreferencesRepository = userRepo)
            )[MainViewModel::class.java]

            AvocadoTheme(
                darkTheme = isDark
            ) {
                val startState by mainViewModel.startDestination.collectAsState()
                var isAdLoading by remember { mutableStateOf(false) }

                // Отслеживаем, проходил ли юзер онбординг сейчас
                if (startState == MainViewModel.StartDestination.Onboarding) {
                    hasSeenOnboardingThisSession = true
                }

                // Инициализируем рекламу ТОЛЬКО для 2+ запуска
                LaunchedEffect(startState) {
                    if (startState == MainViewModel.StartDestination.Main && !hasSeenOnboardingThisSession) {
                        isAdLoading = true
                        // Попали на Main, и онбординга не было = это второй запуск!
                        MobileAds.initialize(this@MainActivity) {
                            val appOpenAdLoader = AppOpenAdLoader(this@MainActivity)
                            val adRequestConfiguration =
                                AdRequestConfiguration.Builder(AdsConfig.APP_OPEN_ADS_ID).build()

                            val appOpenAdLoadListener = object : AppOpenAdLoadListener {
                                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                                    isAdLoading = false
                                    // The ad was loaded successfully. Now you can show loaded ad.
                                    appOpenAd.setAdEventListener(object : AppOpenAdEventListener {
                                        override fun onAdClicked() {}

                                        override fun onAdDismissed() {}

                                        override fun onAdFailedToShow(adError: AdError) {}

                                        override fun onAdImpression(impressionData: ImpressionData?) {}

                                        override fun onAdShown() {}
                                    })
                                    appOpenAd.show(this@MainActivity)
                                }

                                override fun onAdFailedToLoad(error: AdRequestError) {
                                    isAdLoading = false
                                    // Ad failed to load with AdRequestError.
                                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                                }
                            }
                            appOpenAdLoader.setAdLoadListener(appOpenAdLoadListener)
                            appOpenAdLoader.loadAd(adRequestConfiguration)
                        }
                    }
                }

                // Логика загрузки (оставляем снаружи навигации, чтобы не дергать граф)
                if (startState == MainViewModel.StartDestination.Loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.loading),
                                contentDescription = null,
                                modifier = Modifier.size(200.dp)
                            )
                            Spacer(modifier = Modifier.height(Dimen.medium))
                            Text(
                                text = stringResource(R.string.loading),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    // 🔥 РЕШЕНИЕ: Динамически определяем стартовый экран для NavHost
                    val startRoute: Any =
                        if (startState == MainViewModel.StartDestination.Onboarding) {
                            AppDestination.Onboarding
                        } else {
                            AppDestination.MainScreen
                        }

                    // Обработка диплинков
                    LaunchedEffect(destinationRoute) {
                        if (destinationRoute != null && startState == MainViewModel.StartDestination.Main) {
                            val parts = destinationRoute.split("/")
                            if (parts.size == 2) {
                                val type = parts[0]
                                val id = parts[1]

                                when (type) {
                                    "food" -> navController.navigate(
                                        AppDestination.DetailedScreen(
                                            foodId = id
                                        )
                                    )

                                    "receipt" -> navController.navigate(
                                        AppDestination.ReceiptDetailRoute(
                                            receiptId = id
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // 🔥 ЕДИНЫЙ NAVHOST ДЛЯ ВСЕГО ПРИЛОЖЕНИЯ
                    NavHost(
                        navController = navController,
                        startDestination = startRoute
                    ) {
                        // 1. ОНБОРДИНГ ТЕПЕРЬ ВНУТРИ NAVHOST!
                        composable<AppDestination.Onboarding> {
                            OnboardingScreen(
                                onFinish = { goals, restrictions ->
                                    // Сохраняем в память
                                    mainViewModel.finishOnboarding(goals, restrictions)
                                    // Переключаемся на главный экран и очищаем стек, чтобы нельзя было вернуться назад кнопкой "Назад"
                                    navController.navigate(AppDestination.MainScreen) {
                                        popUpTo(AppDestination.Onboarding) { inclusive = true }
                                    }
                                },
                                onPrivacyClick = {
                                    // Теперь navController знает про этот экран и не упадет!
                                    navController.navigate(AppDestination.PrivacyPolicy)
                                }
                            )
                        }

                        // 2. ГЛАВНЫЙ ЭКРАН
                        composable<AppDestination.MainScreen> {
                            MainScreen(
                                userPreferencesRepository = userRepo,
                                settingsViewModel = settingsViewModel,
                                shoppingRepository = shoppingRepo,
                                navController = navController,
                                favoritesRepository = favRepo,
                                mealPlanRepository = mealPlanRepository,
                                waterRepository = waterRepo
                            )
                        }

                        // 3. ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ
                        composable<AppDestination.PrivacyPolicy> {
                            PrivacyPolicyScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        // 4. ДЕТАЛИ ПРОДУКТА
                        composable<AppDestination.DetailedScreen> { backStackEntry ->
                            val args = backStackEntry.toRoute<AppDestination.DetailedScreen>()

                            val viewModel: ListViewModel = viewModel(
                                factory = ListViewModel.Companion.ListViewModelFactory(
                                    favoritesRepository = favRepo,
                                    userPreferencesRepository = userRepo,
                                    context = this@MainActivity,
                                    shoppingRepo = shoppingRepo
                                )
                            )

                            val foodItem = remember { viewModel.getFoodById(args.foodId) }
                            val favorites by viewModel.favoriteIds.collectAsState()

                            if (foodItem != null) {
                                ProductDetailScreen(
                                    item = foodItem,
                                    isFavorite = favorites.contains(foodItem.id),
                                    onBackClick = { navController.popBackStack() },
                                    onLikeClick = { viewModel.onLikeClick(foodItem.id) },
                                    onGetColor = { res, ctx ->
                                        viewModel.getBackgroundColorForFood(
                                            res,
                                            ctx
                                        )
                                    },
                                    onReceiptClick = { receiptId ->
                                        navController.navigate(
                                            AppDestination.ReceiptDetailRoute(receiptId)
                                        )
                                    }
                                )
                            } else {
                                Text("Продукт не найден")
                            }
                        }

                        // 5. ИЗБРАННОЕ
                        composable<AppDestination.Favorite> {
                            FavoriteScreen(
                                favoritesRepository = favRepo,
                                navController = navController,
                                shoppingRepository = shoppingRepo
                            )
                        }

                        // 6. ДЕТАЛИ РЕЦЕПТА
                        composable<AppDestination.ReceiptDetailRoute> { backStackEntry ->
                            val args = backStackEntry.toRoute<AppDestination.ReceiptDetailRoute>()

                            ReceiptDetailScreen(
                                receiptId = args.receiptId,
                                navController = navController,
                                userPreferencesRepository = userRepo,
                                shoppingRepository = shoppingRepo
                            )
                        }

                        composable<AppDestination.ShoppingList> { backStackEntry ->
                            ShoppingScreen(
                                navController = navController,
                                shoppingRepository = shoppingRepo
                            )
                        }

                        composable<AppDestination.Scanner> { backStackEntry ->
                            FoodScannerScreen(
                                navController = navController,
                                userPreferencesRepository = userRepo,
                                shoppingRepository = shoppingRepo
                            )
                        }
                    }
                }
                if (isAdLoading) {
                    IOSLoadingDialog()
                }
            }
        }
    }
}
