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
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSAlertDialog
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSLoadingDialog

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

    val ingredients = remember { viewModel.getIngredients(receipt.relatedFood) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // СОСТОЯНИЯ ДЛЯ КОРЗИНЫ
    var isAddedToCart by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val rawInstructions = stringResource(receipt.receiptText)
    var isAdLoading by remember { mutableStateOf(false) }
    val steps = remember(rawInstructions) {
        rawInstructions.split("\n").filter { it.isNotBlank() }
    }

    var showCookingMode by remember { mutableStateOf(false) }

    val activity = LocalActivity.current

    LaunchedEffect(key1 = Unit) {
        if (AdsConfig.canShowAd()) {
            isAdLoading = true
            interstitialAdLoader = InterstitialAdLoader(context).apply {
                setAdLoadListener(object : InterstitialAdLoadListener {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        AdsConfig.registerAdShown()
                        isAdLoading = false
                        ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd =
                            interstitialAd

                        interstitialAd.apply {
                            setAdEventListener(object : InterstitialAdEventListener {
                                override fun onAdShown() {}

                                override fun onAdFailedToShow(adError: AdError) {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd =
                                        null
                                    navController.popBackStack()
                                }

                                override fun onAdDismissed() {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.main.receipt.details.interstitialAd =
                                        null
                                    navController.popBackStack()
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
            val adRequestConfiguration =
                AdRequestConfiguration.Builder(AdsConfig.INTERSTITIAL_ADS_ID).build()
            interstitialAdLoader?.loadAd(adRequestConfiguration)
        }
    }

    BackHandler(enabled = true) {
        if (AdsConfig.canShowAd()) {
            if (interstitialAd != null && activity != null) {
                interstitialAd?.show(activity = activity)
            } else {
                navController.popBackStack()
            }
        } else {
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- PARALLAX HEADER ---
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            )
        }

        // --- 2. CONTENT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(HEADER_HEIGHT - 32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 800.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimen.large)
            ) {
                // "Ручка"
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                )

                Spacer(modifier = Modifier.height(Dimen.extraLarge))

                Text(
                    text = stringResource(receipt.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Text(
                    text = stringResource(receipt.descRes),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.padding(top = Dimen.medium, bottom = 24.dp)
                )

                // --- МЕТА ДАННЫЕ ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ReceiptMetaBig(
                        Icons.Default.Schedule,
                        stringResource(R.string.format_minutes, receipt.timeMinutes),
                        stringResource(R.string.meta_time)
                    )
                    ReceiptMetaBig(
                        Icons.Default.LocalFireDepartment,
                        stringResource(R.string.format_kcal, receipt.calories),
                        stringResource(R.string.meta_kcal)
                    )
                    val diffText = when (receipt.difficulty) {
                        1 -> stringResource(R.string.diff_easy)
                        2 -> stringResource(R.string.diff_medium)
                        else -> stringResource(R.string.diff_hard)
                    }
                    ReceiptMetaBig(
                        Icons.Default.Bolt,
                        diffText,
                        stringResource(R.string.meta_level)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- КНОПКА "НАЧАТЬ ГОТОВКУ" ---
                Button(
                    onClick = { showCookingMode = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.buttonHeight),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IOSGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        stringResource(R.string.btn_start_cooking),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- ИНГРЕДИЕНТЫ ---
                if (ingredients.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ingredients.forEach { food ->
                            IngredientItem(food = food, onClick = {
                                navController.navigate(AppDestination.DetailedScreen(foodId = food.id))
                            }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimen.medium))

                    // 🛒 КНОПКА ДОБАВИТЬ В КОРЗИНУ
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            // Если добавлено - серый цвет, если нет - светло-зеленый (или как у тебя было)
                            .background(
                                if (isAddedToCart) MaterialTheme.colorScheme.surfaceVariant.copy(
                                    alpha = 0.5f
                                )
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            // Клик работает только если еще НЕ добавлено
                            .then(
                                if (!isAddedToCart) {
                                    Modifier.iosClickable {
                                        // 1. Вызываем метод ViewModel для сохранения в БД
                                        viewModel.addIngredientsToCart(ingredients)
                                        // 2. Меняем состояния для UI
                                        isAddedToCart = true
                                        showSuccessDialog = true
                                    }
                                } else Modifier
                            )
                            .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                // Иконка меняется с Корзины на Галочку
                                imageVector = if (isAddedToCart) Icons.Default.Check else Icons.Rounded.ShoppingCart,
                                contentDescription = null,
                                tint = if (isAddedToCart) MaterialTheme.colorScheme.onSurfaceVariant else IOSGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(Dimen.mediumHalf))
                            Text(
                                // Текст меняется
                                text = if (isAddedToCart) stringResource(R.string.shopping_added_btn)
                                else stringResource(R.string.shopping_add_to_cart),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isAddedToCart) MaterialTheme.colorScheme.onSurfaceVariant else IOSGreen
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }

                // --- ПРИГОТОВЛЕНИЕ ---
                Text(
                    text = stringResource(R.string.cooking),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(bottom = Dimen.medium)
                )

                steps.forEachIndexed { index, step ->
                    StepItem(index + 1, step)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                DisclaimerCard()
                Spacer(modifier = Modifier.height(60.dp))
            }
        }

        // --- BACK BUTTON ---
        Box(
            modifier = Modifier.padding(top = 48.dp, start = 16.dp)
        ) {
            GlassButton(onClick = {
                if (interstitialAd != null && activity != null)
                    interstitialAd?.show(activity = activity)
                else
                    navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

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

    if (isAdLoading) {
        // Рисуем наш полупрозрачный лоадер поверх всего экрана
        IOSLoadingDialog()
    }
}

// =======================
// UI COMPONENTS
// =======================

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

@Composable
fun IngredientItem(food: Food, onClick: () -> Unit, isShort: Boolean = false) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .iosClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(food.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(50.dp)
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
fun StepItem(number: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(IOSGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

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