package ru.plumsoftware.avocado.ui.screen.details

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.main.list.getLightenedColor
import ru.plumsoftware.avocado.data.base.model.food.TimeForFood
import ru.plumsoftware.avocado.ui.screen.details.elements.IOSPopup
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.ads.AdsConfig
import ru.plumsoftware.avocado.data.base.model.receipt.getReceiptsByIds
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSAlertDialog
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSLoadingDialog
import ru.plumsoftware.avocado.ui.screen.main.meal.elements.DailyTotals
import ru.plumsoftware.avocado.ui.screen.main.receipt.elements.SmallReceiptCard
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen

// Высота шапки с картинкой
private val HEADER_HEIGHT = 400.dp
private var interstitialAd: InterstitialAd? = null
private var interstitialAdLoader: InterstitialAdLoader? = null

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    item: Food,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit,
    onGetColor: (Int, Context) -> Int,
    onReceiptClick: (String) -> Unit
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val scrollState = rememberScrollState()

    // 🎨 ЦВЕТА: Ультра-мягкая пастель для iOS-минимализма
    val baseColorInt = remember(item.imageRes) { onGetColor(item.imageRes, context) }
    val dominantColor = Color(baseColorInt)
    // Делаем фон очень светлым (как подложка в Apple Store)
    val topBackgroundColor = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.85f)) }
    val colorStart = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.6f)) }
    val colorEnd = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.2f)) }

    // СОСТОЯНИЯ ЛОГИКИ
    var isAdLoading by remember { mutableStateOf(false) }
    var isAddedToCart by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showCookingMode by remember { mutableStateOf(false) }

    val relatedReceipts = remember(item.relatedReceipts) { getReceiptsByIds(item.relatedReceipts) }
    val ingredients = remember(item) { /* Если это рецепт, тут были бы ингредиенты, но это еда */ emptyList<Food>() }

    // РЕКЛАМА
    LaunchedEffect(key1 = Unit) {
        if (AdsConfig.canShowAd()) {
            isAdLoading = true
            interstitialAdLoader = InterstitialAdLoader(context).apply {
                setAdLoadListener(object : InterstitialAdLoadListener {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        AdsConfig.registerAdShown()
                        isAdLoading = false
                        ru.plumsoftware.avocado.ui.screen.details.interstitialAd = interstitialAd

                        interstitialAd.apply {
                            setAdEventListener(object : InterstitialAdEventListener {
                                override fun onAdShown() {}
                                override fun onAdFailedToShow(adError: AdError) {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.details.interstitialAd = null
                                    onBackClick()
                                }
                                override fun onAdDismissed() {
                                    interstitialAd.setAdEventListener(null)
                                    ru.plumsoftware.avocado.ui.screen.details.interstitialAd = null
                                    onBackClick()
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
            onBackClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. ЧИСТЫЙ МИНИМАЛИСТИЧНЫЙ HEADER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
                .graphicsLayer {
                    translationY = -scrollState.value * 0.5f
                    alpha = 1f - (scrollState.value / HEADER_HEIGHT.toPx())
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(colorStart, colorEnd),
                        radius = 1200f
                    )
                )
        ) {
            Image(
                painter = painterResource(item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.Center)
                    // Мягчайшая тень для объема
                    .shadow(
                        elevation = 32.dp,
                        shape = CircleShape,
                        spotColor = dominantColor.copy(alpha = 0.25f),
                        ambientColor = dominantColor.copy(alpha = 0.05f)
                    )
            )
        }

        // --- 2. КОНТЕНТ (Скроллящийся "Лист бумаги") ---
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
                    .padding(horizontal = Dimen.large, vertical = Dimen.medium)
            ) {
                // "Ручка" (Grabber)
                Box(
                    modifier = Modifier
                        .padding(top = Dimen.extraSmall, bottom = Dimen.extraLarge)
                        .align(Alignment.CenterHorizontally)
                        .width(36.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                )

                // --- ЗАГОЛОВОК И ВРЕМЯ ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(item.titleRes),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = (-0.5).sp
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(Dimen.medium))

                    BestTimeCard(item.timeForFood)
                }

                Spacer(modifier = Modifier.height(Dimen.medium))

                // --- ОПИСАНИЕ ---
                val description = stringResource(item.descRes)
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 24.sp,
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(bottom = Dimen.extraLarge)
                    )
                }

                // --- АНИМИРОВАННЫЕ КБЖУ (Из прошлого шага) ---
                val productTotals = remember(item) {
                    DailyTotals(
                        kals = item.kpfc_100g.kals,
                        proteins = item.kpfc_100g.proteins,
                        fats = item.kpfc_100g.fats,
                        carbs = item.kpfc_100g.carbohydrates
                    )
                }
                ProductStatsRingsWidget(totals = productTotals)

                Spacer(modifier = Modifier.height(Dimen.extraLarge))

                // --- СУПЕР-СИЛА ---
                if (item.kpfc_100g.omega3 >= 0.1 || item.kpfc_100g.fiber >= 0.1) {
                    DetailSectionTitle(stringResource(R.string.title_super_power))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                        modifier = Modifier.fillMaxWidth().padding(bottom = Dimen.extraLarge)
                    ) {
                        if (item.kpfc_100g.omega3 >= 0.1) {
                            IOSSimpleChip(
                                title = stringResource(R.string.omega3),
                                value = "${item.kpfc_100g.omega3}г",
                                bgColor = Color(0xFFFFF3E0),
                                textColor = Color(0xFFE65100)
                            )
                        }
                        if (item.kpfc_100g.fiber >= 0.1) {
                            IOSSimpleChip(
                                title = stringResource(R.string.fiber),
                                value = "${item.kpfc_100g.fiber}г",
                                bgColor = Color(0xFFE8F5E9),
                                textColor = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // --- ВИТАМИНЫ И МИНЕРАЛЫ ---
                if (item.vitamins.isNotEmpty() || item.minerals.isNotEmpty()) {
                    DetailSectionTitle("Состав микроэлементов") // Вынеси в ресурсы
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                        verticalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                        modifier = Modifier.padding(bottom = Dimen.extraLarge)
                    ) {
                        item.vitamins.forEach { vitamin ->
                            IOSInfoTag(stringResource(vitamin.title), vitamin.healthyFor)
                        }
                        item.minerals.forEach { mineral ->
                            IOSInfoTag(stringResource(mineral.title), mineral.healthyFor)
                        }
                    }
                }

                // --- РЕЦЕПТЫ С ЭТИМ ПРОДУКТОМ ---
                if (relatedReceipts.isNotEmpty()) {
                    DetailSectionTitle(stringResource(R.string.title_cook_with_this))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                        modifier = Modifier.padding(bottom = Dimen.extraLarge)
                    ) {
                        items(relatedReceipts) { receipt ->
                            SmallReceiptCard(
                                receipt = receipt,
                                onClick = { onReceiptClick(receipt.id) }
                            )
                        }
                    }
                }

                // --- КНОПКА КОРЗИНЫ ---
                Button(
                    onClick = {
                        // viewModel.addIngredientsToCart(listOf(item)) // Если нужна логика
                        isAddedToCart = true
                        showSuccessDialog = true
                    },
                    enabled = !isAddedToCart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IOSGreen,
                        contentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp) // Плоская кнопка iOS
                ) {
                    Icon(
                        imageVector = if (isAddedToCart) Icons.Default.Check else Icons.Rounded.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isAddedToCart) stringResource(R.string.shopping_added_btn) else stringResource(R.string.shopping_add_to_cart),
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
                } else onBackClick()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Стрелка
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            GlassButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.cd_favorite),
                    tint = if (isFavorite) Color(0xFFFF3B30) else MaterialTheme.colorScheme.onSurface // Красный цвет iOS
                )
            }
        }
    }

    // --- ДИАЛОГИ ---
    if (isAdLoading) IOSLoadingDialog()
    if (showSuccessDialog) {
        IOSAlertDialog(
            title = stringResource(R.string.dialog_added_title),
            message = stringResource(R.string.dialog_added_msg),
            buttonText = stringResource(R.string.dialog_btn_ok),
            onDismiss = { showSuccessDialog = false }
        )
    }
}

// ==========================================
// УЛЬТРА-ЧИСТЫЕ UI КОМПОНЕНТЫ
// ==========================================

@Composable
fun IOSSimpleChip(title: String, value: String, bgColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = textColor.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = value, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = textColor)
    }
}

@Composable
fun IOSInfoTag(text: String, @StringRes healthyForRes: Int) {
    var showTooltip by remember { mutableStateOf(false) }
    val hasInfo = healthyForRes != 0

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                .then(if (hasInfo) Modifier.iosClickable { showTooltip = true } else Modifier)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
            if (hasInfo) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (hasInfo) {
            IOSPopup(text = stringResource(healthyForRes), isVisible = showTooltip, onDismiss = { showTooltip = false })
        }
    }
}

@Composable
fun DetailSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            letterSpacing = (-0.5).sp
        ),
        modifier = Modifier.padding(bottom = Dimen.medium)
    )
}

@Composable
fun ProductStatsRingsWidget(totals: DailyTotals) {
    // 🔥 Масштаб для одного продукта.
    // Делаем лимиты поменьше (например, 1/3 от суточной нормы),
    // чтобы кольца красиво заполнялись даже от 100г продукта.
    val scaleFactor = 0.33f
    val goalKcal = 2000f * scaleFactor
    val goalProteins = 100f * scaleFactor
    val goalFats = 70f * scaleFactor
    val goalCarbs = 250f * scaleFactor

    val animKcal by animateIntAsState(targetValue = totals.kals, animationSpec = tween(1000), label = "kcal")
    val pKcal by animateFloatAsState(targetValue = (totals.kals / goalKcal).coerceIn(0f, 1f), animationSpec = tween(1000), label = "pKcal")
    val pProt by animateFloatAsState(targetValue = (totals.proteins / goalProteins).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pProt")
    val pFats by animateFloatAsState(targetValue = (totals.fats / goalFats).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pFats")
    val pCarbs by animateFloatAsState(targetValue = (totals.carbs / goalCarbs).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pCarbs")

    val colorKcal = Color(0xFFFF3B30) // Красный iOS (Ккал)
    val colorProt = Color(0xFF34C759) // Зеленый iOS (Белки)
    val colorFats = Color(0xFFFF9500) // Оранжевый iOS (Жиры)
    val colorCarbs = Color(0xFF007AFF) // Синий iOS (Углеводы)

    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(Dimen.large))
            .padding(Dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ЛЕВАЯ ЧАСТЬ: Цифры
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Ценность на 100г", // Можно вынести в ресурсы
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$animKcal",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = " ккал", // Вынести в ресурсы
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Легенда БЖУ
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatsLegendItem(stringResource(R.string.macro_proteins), totals.proteins.toInt(), colorProt)
                    StatsLegendItem(stringResource(R.string.macro_fats), totals.fats.toInt(), colorFats)
                    StatsLegendItem(stringResource(R.string.macro_carbs), totals.carbs.toInt(), colorCarbs)
                }
            }

            // ПРАВАЯ ЧАСТЬ: КОЛЬЦА АКТИВНОСТИ
            Box(
                modifier = Modifier.size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(90.dp)) {
                    val strokeWidth = 14f
                    val spacing = 20f
                    val center = center

                    val radius1 = size.minDimension / 2 - strokeWidth / 2
                    val radius2 = radius1 - spacing
                    val radius3 = radius2 - spacing
                    val radius4 = radius3 - spacing

                    fun drawRing(radius: Float, progress: Float, activeColor: Color) {
                        drawCircle(color = trackColor, radius = radius, center = center, style = Stroke(
                            width = strokeWidth
                        )
                        )
                        if (progress > 0) {
                            drawArc(
                                color = activeColor,
                                startAngle = -90f,
                                sweepAngle = 360f * progress,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius),
                                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                            )
                        }
                    }

                    drawRing(radius1, pKcal, colorKcal)
                    drawRing(radius2, pProt, colorProt)
                    drawRing(radius3, pFats, colorFats)
                    drawRing(radius4, pCarbs, colorCarbs)
                }
            }
        }
    }
}

// Если у тебя еще нет StatsLegendItem в этом файле, добавь:
@Composable
fun StatsLegendItem(label: String, value: Int, color: Color) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "${value}г",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// ==========================================
// ВСПОМОГАТЕЛЬНЫЕ КОМПОНЕНТЫ
// ==========================================

@Composable
fun BrightMacrosBlock(kpfc: Kpfc) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BrightMacroCard(
            modifier = Modifier.weight(1f),
            value = "${kpfc.proteins}",
            label = "Белки",
            backgroundColor = Color(0xFF34C759).copy(alpha = 0.15f),
            contentColor = Color(0xFF248A3D)
        )
        BrightMacroCard(
            modifier = Modifier.weight(1f),
            value = "${kpfc.fats}",
            label = "Жиры",
            backgroundColor = Color(0xFFFF9500).copy(alpha = 0.15f),
            contentColor = Color(0xFFC97600)
        )
        BrightMacroCard(
            modifier = Modifier.weight(1f),
            value = "${kpfc.carbohydrates}",
            label = "Углев.",
            backgroundColor = Color(0xFF007AFF).copy(alpha = 0.15f),
            contentColor = Color(0xFF0056B3)
        )
    }
}

@Composable
fun BrightMacroCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = contentColor
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = contentColor.copy(alpha = 0.8f)
            )
        )
    }
}

@Composable
fun BestTimeCard(time: TimeForFood) {
    val (textRes, icon, color) = getTimeForFoodResources(time)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.15f)) // Пастельный фон
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        )
    }
}

@Composable
fun VitaminChip(text: String, @StringRes healthyForRes: Int) {
    var showTooltip by remember { mutableStateOf(false) }
    val hasInfo = healthyForRes != 0

    Box {
        Row(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .then(if (hasInfo) Modifier.iosClickable { showTooltip = true } else Modifier)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            if (hasInfo) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }
        if (hasInfo) {
            IOSPopup(
                text = stringResource(healthyForRes),
                isVisible = showTooltip,
                onDismiss = { showTooltip = false })
        }
    }
}

@Composable
fun MineralChip(text: String, @StringRes healthyForRes: Int) {
    var showTooltip by remember { mutableStateOf(false) }
    val hasInfo = healthyForRes != 0

    Box {
        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .then(if (hasInfo) Modifier.iosClickable { showTooltip = true } else Modifier)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
            )
            if (hasInfo) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f)
                )
            }
        }
        if (hasInfo) {
            IOSPopup(
                text = stringResource(healthyForRes),
                isVisible = showTooltip,
                onDismiss = { showTooltip = false })
        }
    }
}

@Composable
fun DisclaimerCard() {
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    val backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.5f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Disclaimer",
            tint = contentColor.copy(alpha = 0.8f),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = stringResource(R.string.disclaimer_title),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.disclaimer_body),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = contentColor.copy(alpha = 0.7f),
                    lineHeight = 16.sp,
                    fontSize = 11.sp
                )
            )
        }
    }
}

//@Composable
//fun DetailSectionTitle(title: String) {
//    Text(
//        text = title,
//        style = MaterialTheme.typography.titleMedium.copy(
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onSurface
//        ),
//        modifier = Modifier.padding(bottom = 12.dp)
//    )
//}

@Composable
fun GlassButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            .iosClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun SpecialChip(title: String, value: String, backgroundColor: Color, contentColor: Color) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(color = contentColor.copy(alpha = 0.8f))
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            )
        }
    }
}

// Вспомогательная функция для ресурсов времени (убедись что строки есть в strings.xml)
@Composable
fun getTimeForFoodResources(time: TimeForFood): Triple<Int, ImageVector, Color> {
    return when (time) {
        TimeForFood.BREAKFAST -> Triple(
            R.string.time_breakfast,
            Icons.Default.WbSunny,
            Color(0xFFFF9F0A)
        )

        TimeForFood.LUNCH -> Triple(
            R.string.time_lunch,
            Icons.Default.Restaurant,
            Color(0xFF34C759)
        )

        TimeForFood.DINNER -> Triple(
            R.string.time_dinner,
            Icons.Default.NightsStay,
            Color(0xFF5E5CE6)
        )

        TimeForFood.SNACK -> Triple(R.string.time_snack, Icons.Default.Eco, Color(0xFF30B0C7))
        TimeForFood.ANY -> Triple(R.string.time_any, Icons.Default.Schedule, Color(0xFF8E8E93))
    }
}