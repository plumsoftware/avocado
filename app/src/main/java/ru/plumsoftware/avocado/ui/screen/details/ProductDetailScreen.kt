package ru.plumsoftware.avocado.ui.screen.details

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.StringRes
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import ru.plumsoftware.avocado.ui.screen.main.elements.IOSLoadingDialog
import ru.plumsoftware.avocado.ui.screen.main.receipt.elements.SmallReceiptCard

// Высота шапки с картинкой
private val HEADER_HEIGHT = 400.dp
private var interstitialAd: InterstitialAd? = null
private var interstitialAdLoader: InterstitialAdLoader? = null

@OptIn(ExperimentalLayoutApi::class)
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

    // Генерация цветов для градиента
    val baseColorInt = remember(item.imageRes) { onGetColor(item.imageRes, context) }
    val colorStart = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.6f)) }
    val colorEnd = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.2f)) }
    var isAdLoading by remember { mutableStateOf(false) }
    val dominantColor = Color(baseColorInt)
    val relatedReceipts = remember(item.relatedReceipts) {
        getReceiptsByIds(item.relatedReceipts)
    }

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
                onBackClick()
            }
        } else {
            onBackClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. PARALLAX HEADER (Фон + Картинка) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_HEIGHT)
                .graphicsLayer {
                    // Картинка движется в 2 раза медленнее скролла (эффект глубины)
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
                    .size(260.dp)
                    .align(Alignment.Center)
                    .shadow(
                        elevation = 40.dp,
                        shape = CircleShape,
                        spotColor = dominantColor.copy(alpha = 0.4f),
                        ambientColor = dominantColor.copy(alpha = 0.1f)
                    )
            )
        }

        // --- 2. SCROLLABLE CONTENT (Белый лист) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Прозрачный отступ, чтобы контент начинался ниже картинки
            Spacer(modifier = Modifier.height(HEADER_HEIGHT - 40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 800.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimen.large)
            ) {
                // "Ручка" шторки
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
                )

                Spacer(modifier = Modifier.height(20.dp))

                // --- ЗАГОЛОВОК ---
                Text(
                    text = stringResource(item.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                // --- КАЛОРИИ И ВРЕМЯ ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = Dimen.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.kpfc_100g.kals} ккал",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    BestTimeCard(item.timeForFood)
                }

                // --- ОПИСАНИЕ ПРОДУКТА (Новое поле) ---
                // Используем try/catch на случай, если resource ID не найден (безопасность)
                val description = stringResource(item.descRes)
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                            lineHeight = 24.sp, // Хороший межстрочный интервал для чтения
                            fontSize = 17.sp
                        ),
                        modifier = Modifier.padding(bottom = Dimen.large)
                    )
                }

                // --- ЯРКИЕ КБЖУ ---
                BrightMacrosBlock(item.kpfc_100g)

                Spacer(modifier = Modifier.height(32.dp))

                // --- СПЕЦИАЛЬНЫЕ СВОЙСТВА (Омега, Клетчатка) ---
                if (item.kpfc_100g.omega3 >= 0.1 || item.kpfc_100g.fiber >= 0.1) {
                    DetailSectionTitle("Супер-сила")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumAboveHalf),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (item.kpfc_100g.omega3 >= 0.1) {
                            SpecialChip(
                                title = stringResource(R.string.omega3), // Убедись что есть ресурс
                                value = "${item.kpfc_100g.omega3}г",
                                backgroundColor = Color(0xFFFFF8E1), // Золотой
                                contentColor = Color(0xFFFF8F00)
                            )
                        }
                        if (item.kpfc_100g.fiber >= 0.1) {
                            SpecialChip(
                                title = stringResource(R.string.fiber),
                                value = "${item.kpfc_100g.fiber}г",
                                backgroundColor = Color(0xFFE8F5E9), // Зеленый
                                contentColor = Color(0xFF2E7D32)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- ВИТАМИНЫ ---
                if (item.vitamins.isNotEmpty()) {
                    DetailSectionTitle("Витамины")
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item.vitamins.forEach { vitamin ->
                            VitaminChip(
                                text = stringResource(vitamin.title),
                                healthyForRes = vitamin.healthyFor
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- МИНЕРАЛЫ ---
                if (item.minerals.isNotEmpty()) {
                    DetailSectionTitle("Минералы")
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item.minerals.forEach { mineral ->
                            MineralChip(
                                text = stringResource(mineral.title),
                                healthyForRes = mineral.healthyFor
                            )
                        }
                    }
                }

                if (relatedReceipts.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Заголовок секции
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DetailSectionTitle("Готовим с этим")
                    }

                    // Горизонтальный список
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                        contentPadding = PaddingValues(horizontal = Dimen.extraSmall)
                    ) {
                        items(relatedReceipts) { receipt ->
                            SmallReceiptCard(
                                receipt = receipt,
                                onClick = { onReceiptClick(receipt.id) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- DISCLAIMER ---
                DisclaimerCard()

                Spacer(modifier = Modifier.height(120.dp)) // Отступ снизу
            }
        }

        // --- 3. НАВИГАЦИЯ (Поверх всего) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 48.dp,
                    start = Dimen.medium,
                    end = Dimen.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlassButton(onClick = {
                if (interstitialAd != null && activity != null)
                    interstitialAd?.show(activity = activity)
                else
                    onBackClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            GlassButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (isAdLoading) {
        // Рисуем наш полупрозрачный лоадер поверх всего экрана
        IOSLoadingDialog()
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

@Composable
fun DetailSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

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