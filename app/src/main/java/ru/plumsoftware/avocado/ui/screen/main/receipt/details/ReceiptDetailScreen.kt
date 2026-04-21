package ru.plumsoftware.avocado.ui.screen.main.receipt.details

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.details.DisclaimerCard
import ru.plumsoftware.avocado.ui.screen.details.GlassButton
import ru.plumsoftware.avocado.ui.screen.main.receipt.RecipesViewModel
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.ads.AdsConfig
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.ui.screen.details.DetailSectionTitle
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSAlertDialog
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSLoadingDialog
import ru.plumsoftware.avocado.ui.screen.main.meal.elements.DailyTotals

private val HEADER_HEIGHT = 380.dp
private var interstitialAd: InterstitialAd? = null
private var interstitialAdLoader: InterstitialAdLoader? = null

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDetailScreen(
    receiptId: String,
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository,
    shoppingRepository: ShoppingRepository
) {
    val viewModel: RecipesViewModel =
        viewModel(
            factory = RecipesViewModel.Factory(
                userPrefsRepo = userPreferencesRepository,
                shoppingRepository = shoppingRepository
            )
        )
    val receipt = remember { viewModel.getReceiptById(receiptId) }

    if (receipt == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                stringResource(R.string.receipt_not_found),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        return
    }

    // --- ИНГРЕДИЕНТЫ ---
    val ingredients = remember { viewModel.getIngredients(receipt.relatedFood) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val activity = LocalActivity.current

    // 🔥 НОВОЕ: Динамический подсчет БЖУ на основе ингредиентов
    val recipeTotals = remember(receipt, ingredients) {
        var proteins = 0.0
        var fats = 0.0
        var carbs = 0.0

        ingredients.forEach { food ->
            proteins += food.kpfc_100g.proteins
            fats += food.kpfc_100g.fats
            carbs += food.kpfc_100g.carbohydrates
        }

        DailyTotals(
            kals = receipt.calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs
        )
    }

    // СОСТОЯНИЯ
    var isAddedToCart by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showCookingMode by remember { mutableStateOf(false) }
    var isAdLoading by remember { mutableStateOf(false) }

    val rawInstructions = stringResource(receipt.receiptText)
    val steps = remember(rawInstructions) {
        rawInstructions.split("\n").filter { it.isNotBlank() }
    }

    // РЕКЛАМА
    LaunchedEffect(key1 = Unit) {
        if (AdsConfig.canShowAd()) {
            isAdLoading = true
            interstitialAdLoader = InterstitialAdLoader(context).apply {
                setAdLoadListener(object : InterstitialAdLoadListener {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        AdsConfig.registerAdShown()
                        isAdLoading = false
                        ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd = interstitialAd

                        interstitialAd.apply {
                            setAdEventListener(object : InterstitialAdEventListener {
                                override fun onAdShown() {}
                                override fun onAdFailedToShow(adError: AdError) {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd = null
                                    navController.navigateUp()
                                }
                                override fun onAdDismissed() {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd = null
                                    navController.navigateUp()
                                }
                                override fun onAdClicked() {}
                                override fun onAdImpression(impressionData: ImpressionData?) {}
                            })
                        }
                    }
                    override fun onAdFailedToLoad(error: AdRequestError) {
                        isAdLoading = false
                    }
                })
            }
            val adRequestConfiguration = AdRequestConfiguration.Builder(AdsConfig.INTERSTITIAL_ADS_ID).build()
            interstitialAdLoader?.loadAd(adRequestConfiguration)
        }
    }

    BackHandler(enabled = true) {
        if (AdsConfig.canShowAd() && interstitialAd != null && activity != null) {
            interstitialAd?.show(activity = activity)
        } else {
            navController.navigateUp()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. ПЛАВНЫЙ HEADER (Без резких теней, легкий градиент) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
                .graphicsLayer {
                    translationY = -scrollState.value * 0.5f
                    alpha = 1f - (scrollState.value / HEADER_HEIGHT.toPx())
                }
        ) {
            Image(
                painter = painterResource(receipt.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Плавное растворение в цвет фона
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface),
                            startY = HEADER_HEIGHT.value * 1.5f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        // --- 2. CONTENT (Чистый скролл) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(HEADER_HEIGHT - 40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 800.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = Dimen.large, vertical = Dimen.medium)
            ) {
                // "Ручка"
                Box(
                    modifier = Modifier
                        .padding(top = Dimen.extraSmall, bottom = Dimen.extraLarge)
                        .align(Alignment.CenterHorizontally)
                        .width(36.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                )

                // --- ЗАГОЛОВОК ---
                Text(
                    text = stringResource(receipt.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = (-0.5).sp
                    )
                )

                // --- ОПИСАНИЕ ---
                Text(
                    text = stringResource(receipt.descRes),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 24.sp,
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.padding(top = Dimen.mediumHalf, bottom = Dimen.extraLarge)
                )

                // 🔥 НОВОЕ: ВИДЖЕТ ПИЩЕВОЙ ЦЕННОСТИ (КОЛЬЦА)
                RecipeMacrosRingWidget(
                    totals = recipeTotals,
                    timeMinutes = receipt.timeMinutes,
                    difficulty = receipt.difficulty
                )

                Spacer(modifier = Modifier.height(Dimen.extraLarge))

                if (ingredients.isNotEmpty()) {
                    DetailSectionTitle(stringResource(R.string.title_ingredients))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ingredients.forEach { food ->
                            IngredientItem(food = food, onClick = {
                                navController.navigate(AppDestination.DetailedScreen(foodId = food.id))
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimen.medium))

                    // 🛒 ПЛОСКАЯ КНОПКА ДОБАВИТЬ В КОРЗИНУ (iOS Style)
                    Button(
                        onClick = {
                            viewModel.addIngredientsToCart(ingredients)
                            isAddedToCart = true
                            showSuccessDialog = true
                        },
                        enabled = !isAddedToCart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            contentColor = IOSGreen,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Icon(
                            imageVector = if (isAddedToCart) Icons.Default.Check else Icons.Rounded.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAddedToCart) stringResource(R.string.shopping_added_btn) else stringResource(R.string.shopping_add_to_cart),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimen.medium))
                }

                Spacer(modifier = Modifier.height(Dimen.extraLarge))

                // --- ПРИГОТОВЛЕНИЕ ---
                DetailSectionTitle(stringResource(R.string.cooking))

                steps.forEachIndexed { index, step ->
                    StepItem(index + 1, step)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(Dimen.medium))

                // --- КНОПКА "НАЧАТЬ ГОТОВКУ" ---
                Button(
                    onClick = { showCookingMode = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IOSGreen, contentColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        stringResource(R.string.btn_start_cooking),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(Dimen.extraLarge))
                DisclaimerCard()
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // --- 3. НАВИГАЦИОННЫЕ КНОПКИ ПОВЕРХ ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = Dimen.medium, end = Dimen.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlassButton(onClick = {
                if (AdsConfig.canShowAd() && interstitialAd != null && activity != null) {
                    interstitialAd?.show(activity = activity)
                } else navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    // ДИАЛОГИ
    if (showCookingMode) {
        CookingModeSheet(
            title = stringResource(receipt.titleRes),
            ingredients = ingredients,
            steps = steps,
            onDismiss = { showCookingMode = false }
        )
    }

    if (showSuccessDialog) {
        IOSAlertDialog(
            title = stringResource(R.string.dialog_added_title),
            message = stringResource(R.string.dialog_added_msg),
            buttonText = stringResource(R.string.dialog_btn_ok),
            onDismiss = { showSuccessDialog = false }
        )
    }

    if (isAdLoading) IOSLoadingDialog()
}

// =======================
// UI COMPONENTS
// =======================

@Composable
fun ReceiptMetaMinimal(icon: ImageVector, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = IOSGreen,
            modifier = Modifier.size(22.dp).padding(bottom = 4.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        )
    }
}

// IngredientItem и StepItem уже были обновлены в предыдущем ответе для минимализма
// Но я дублирую их здесь для целостности картины

@Composable
fun IngredientItem(food: Food, onClick: () -> Unit, isShort: Boolean = false) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .iosClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp) // Чуть меньше и аккуратнее
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(food.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
        }

        if (!isShort) {
            Spacer(modifier = Modifier.height(Dimen.mediumHalf))
            Text(
                text = stringResource(food.titleRes),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ReceiptMetaBig(icon: ImageVector, value: String, label: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = IOSGreen,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        )
    }
}

//@Composable
//fun IngredientItem(food: Food, onClick: () -> Unit, isShort: Boolean = false) {
//    Column(
//        modifier = Modifier
//            .wrapContentSize()
//            .iosClickable { onClick() },
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = Modifier
//                .size(70.dp)
//                .clip(CircleShape)
//                .background(MaterialTheme.colorScheme.surfaceVariant)
//                .border(
//                    1.dp,
//                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
//                    CircleShape
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(food.imageRes),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.size(50.dp)
//            )
//        }
//
//        if (!isShort) {
//            Spacer(modifier = Modifier.height(Dimen.mediumHalf))
//
//            Text(
//                text = stringResource(food.titleRes),
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontWeight = FontWeight.Medium,
//                    color = MaterialTheme.colorScheme.onSurface
//                ),
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//    }
//}

@Composable
fun StepItem(number: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(IOSGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.width(Dimen.medium))

        val cleanText = text.replaceFirst(Regex("^\\d+\\.\\s*"), "")
        Text(
            text = cleanText,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}