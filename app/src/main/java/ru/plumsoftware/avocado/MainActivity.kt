package ru.plumsoftware.avocado

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.data.user_preferences.util.userPreferencesDataStore
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.details.ProductDetailScreen
import ru.plumsoftware.avocado.ui.screen.main.MainScreen
import ru.plumsoftware.avocado.ui.screen.main.MainViewModel
import ru.plumsoftware.avocado.ui.screen.main.favorite.FavoriteScreen
import ru.plumsoftware.avocado.ui.screen.main.list.ListViewModel
import ru.plumsoftware.avocado.ui.screen.main.receipt.details.ReceiptDetailScreen
import ru.plumsoftware.avocado.ui.screen.main.settings.SettingsViewModel
import ru.plumsoftware.avocado.ui.screen.onboarding.OnboardingScreen
import ru.plumsoftware.avocado.ui.theme.AvocadoTheme
import ru.plumsoftware.avocado.ui.theme.Dimen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем БД
        val db = AvocadoDatabase.getDatabase(this)
        // 2. Создаем Репозиторий
        val favRepo = FavoritesRepository(db.favoriteDao())
        val userRepo = UserPreferencesRepository(this.userPreferencesDataStore)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = TRANSPARENT,
                darkScrim = TRANSPARENT
            )
        )

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemGestures()
            )
            view.updatePadding(
                0,
                0,
                0,
                0
            )
            WindowInsetsCompat.CONSUMED
        }


        setContent {

            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModel.Factory(repo = userRepo)
            )

            val themeState by settingsViewModel.currentTheme.collectAsState()

            val isDark = when (themeState) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> isSystemInDarkTheme()
            }

            val mainViewModel = ViewModelProvider(
                this,
                MainViewModel.Companion.MainViewModelFactory(userPreferencesRepository = userRepo)
            )[MainViewModel::class.java]

            AvocadoTheme(
                darkTheme = isDark
            ) {
                val startState by mainViewModel.startDestination.collectAsState()

                when (startState) {
                    MainViewModel.StartDestination.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.loading),
                                    contentDescription = null,
                                    modifier = Modifier.size(200.dp)
                                )

                                Spacer(modifier = Modifier.height(Dimen.medium))

                                Text(
                                    text = stringResource(R.string.loading),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    MainViewModel.StartDestination.Onboarding -> {
                        OnboardingScreen(
                            onFinish = { goals, restrictions ->
                                mainViewModel.finishOnboarding(goals, restrictions)
                            }
                        )
                    }

                    MainViewModel.StartDestination.Main -> {
                        NavHost(
                            navController = navController,
                            startDestination = AppDestination.MainScreen
                        ) {
                            composable<AppDestination.MainScreen> {
                                MainScreen(
                                    userPreferencesRepository = userRepo,
                                    settingsViewModel = settingsViewModel,
                                    navController = navController,
                                    favoritesRepository = favRepo
                                )
                            }

                            composable<AppDestination.DetailedScreen> { backStackEntry ->
                                val args = backStackEntry.toRoute<AppDestination.DetailedScreen>()

                                val viewModel: ListViewModel = viewModel(
                                    factory = ListViewModel.Companion.ListViewModelFactory(
                                        favoritesRepository = favRepo,
                                        userPreferencesRepository = userRepo,
                                        context = this@MainActivity
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
                                            navController.navigate(AppDestination.ReceiptDetailRoute(receiptId))
                                        }
                                    )
                                } else {
                                    Text("Продукт не найден")
                                }
                            }

                            composable<AppDestination.Onboarding> {
                                OnboardingScreen(
                                    onFinish = { goals, restrictions ->
                                        // Сохраняем новые настройки
                                        mainViewModel.finishOnboarding(goals, restrictions)

                                        // Возвращаемся назад в настройки (закрываем онбординг)
                                        navController.popBackStack()
                                    }
                                )
                            }

                            composable<AppDestination.Favorite> {
                                FavoriteScreen(
                                    favoritesRepository = favRepo,
                                    navController = navController
                                )
                            }

                            composable<AppDestination.ReceiptDetailRoute> { backStackEntry ->
                                val args = backStackEntry.toRoute<AppDestination.ReceiptDetailRoute>()

                                ReceiptDetailScreen(
                                    receiptId = args.receiptId,
                                    navController = navController,
                                    userPreferencesRepository = userRepo
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
