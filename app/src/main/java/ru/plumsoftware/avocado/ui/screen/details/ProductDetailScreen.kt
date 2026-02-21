package ru.plumsoftware.avocado.ui.screen.details

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.outlined.HelpOutline
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
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.main.list.getLightenedColor
import ru.plumsoftware.avocado.data.base.model.food.TimeForFood
import ru.plumsoftware.avocado.ui.screen.details.elements.IOSPopup
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R

// Высота шапки с картинкой
private val HEADER_HEIGHT = 400.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductDetailScreen(
    item: Food,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit,
    onGetColor: (Int, Context) -> Int
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Цвета
    val baseColorInt = remember(item.imageRes) { onGetColor(item.imageRes, context) }
    val colorStart = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.6f)) }
    val colorEnd = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.2f)) }
    val dominantColor = Color(baseColorInt)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. PARALLAX HEADER ---
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

        // --- 2. SCROLLABLE CONTENT ---
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

                Spacer(modifier = Modifier.height(20.dp))

                // Заголовок
                Text(
                    text = stringResource(item.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                // Блок: Калории + Время приема пищи (В одну строку или рядом)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimen.large),
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

                    // НОВОЕ: Карточка времени
                    BestTimeCard(item.timeForFood)
                }

                // --- НОВЫЕ ЯРКИЕ КБЖУ (iOS Style) ---
                BrightMacrosBlock(item.kpfc_100g)

                Spacer(modifier = Modifier.height(32.dp))

                // --- СПЕЦИАЛЬНЫЕ СВОЙСТВА ---
                if (item.kpfc_100g.omega3 >= 0.1 || item.kpfc_100g.fiber >= 0.1) {
                    DetailSectionTitle("Польза на 100г")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumAboveHalf),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (item.kpfc_100g.omega3 >= 0.1) {
                            SpecialChip(
                                title = "Омега-3",
                                value = "${item.kpfc_100g.omega3}г",
                                // Золотой
                                backgroundColor = Color(0xFFFFF8E1),
                                contentColor = Color(0xFFFF8F00)
                            )
                        }

                        if (item.kpfc_100g.fiber >= 0.1) {
                            SpecialChip(
                                title = "Клетчатка",
                                value = "${item.kpfc_100g.fiber}г",
                                // Зеленый
                                backgroundColor = Color(0xFFE8F5E9),
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

                Spacer(modifier = Modifier.height(32.dp))

                // --- DISCLAIMER (ПЛАШКА С ПРЕДУПРЕЖДЕНИЕМ) ---
                DisclaimerCard()

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // --- НАВИГАЦИЯ ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = Dimen.medium, end = Dimen.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlassButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
}

@Composable
fun BrightMacrosBlock(kpfc: Kpfc) {
    // Используем Row с весами, чтобы карточки были одинаковой ширины
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Белки (Зеленый Apple Health)
        BrightMacroCard(
            modifier = Modifier.weight(1f),
            value = "${kpfc.proteins}",
            label = "Белки",
            // Фон: яркий, но полупрозрачный
            backgroundColor = Color(0xFF34C759).copy(alpha = 0.15f),
            // Текст: насыщенный
            contentColor = Color(0xFF248A3D)
        )

        // Жиры (Оранжевый)
        BrightMacroCard(
            modifier = Modifier.weight(1f),
            value = "${kpfc.fats}",
            label = "Жиры",
            backgroundColor = Color(0xFFFF9500).copy(alpha = 0.15f),
            contentColor = Color(0xFFC97600)
        )

        // Углеводы (Синий)
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
            style = MaterialTheme.typography.titleLarge.copy( // Крупнее
                fontWeight = FontWeight.ExtraBold, // Жирнее
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

    // Делаем фон очень прозрачным (пастельным), а контент насыщенным
    val backgroundColor = color.copy(alpha = 0.15f)
    val contentColor = color

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50)) // Pill shape
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        )
    }
}

// Вспомогательная функция с цветами iOS
@Composable
fun getTimeForFoodResources(time: TimeForFood): Triple<Int, ImageVector, Color> {
    return when (time) {
        // Утро: Солнечный желто-оранжевый (System Orange)
        TimeForFood.BREAKFAST -> Triple(
            R.string.time_breakfast,
            Icons.Default.WbSunny,
            Color(0xFFFF9F0A)
        )

        // Обед: Свежий зеленый или мятный (System Green)
        TimeForFood.LUNCH -> Triple(
            R.string.time_lunch,
            Icons.Default.Restaurant,
            Color(0xFF34C759)
        )

        // Ужин: Спокойный Индиго (System Indigo)
        TimeForFood.DINNER -> Triple(
            R.string.time_dinner,
            Icons.Default.NightsStay,
            Color(0xFF5E5CE6)
        )

        // Перекус: Бирюзовый (System Teal)
        TimeForFood.SNACK -> Triple(R.string.time_snack, Icons.Default.Eco, Color(0xFF30B0C7))

        // Любое: Нейтральный серый (System Gray)
        TimeForFood.ANY -> Triple(R.string.time_any, Icons.Default.Schedule, Color(0xFF8E8E93))
    }
}

@Composable
fun VitaminChip(
    text: String,
    @StringRes healthyForRes: Int
) {
    var showTooltip by remember { mutableStateOf(false) }
    // Если ресурса нет (0), считаем, что инфо нет
    val hasInfo = healthyForRes != 0

    Box {
        // Сам Чип
        Row(
            modifier = Modifier
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant,
                    RoundedCornerShape(Dimen.mediumAboveHalf)
                )
                .clip(RoundedCornerShape(Dimen.mediumAboveHalf))
                // Если есть инфо - добавляем клик
                .then(
                    if (hasInfo) {
                        Modifier.iosClickable { showTooltip = true }
                    } else Modifier
                )
                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            // Иконка-подсказка (Вопросик или Info)
            if (hasInfo) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Outlined.Info, // Или Icons.Outlined.HelpOutline
                    contentDescription = "Info",
                    modifier = Modifier.size(16.dp), // Маленькая и аккуратная
                    // Цвет текста, но полупрозрачный (вторичный)
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }

        // Подсказка
        if (hasInfo) {
            IOSPopup(
                text = stringResource(healthyForRes),
                isVisible = showTooltip,
                onDismiss = { showTooltip = false }
            )
        }
    }
}

@Composable
fun MineralChip(
    text: String,
    @StringRes healthyForRes: Int
) {
    var showTooltip by remember { mutableStateOf(false) }
    val hasInfo = healthyForRes != 0

    Box {
        Row(
            modifier = Modifier
                .background(
                    // Чуть голубоватый серый (iOS System Fill) для минералов
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    RoundedCornerShape(Dimen.mediumAboveHalf)
                )
                .clip(RoundedCornerShape(Dimen.mediumAboveHalf))
                .then(
                    if (hasInfo) {
                        Modifier.iosClickable { showTooltip = true }
                    } else Modifier
                )
                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumAboveHalf),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
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
                onDismiss = { showTooltip = false }
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
        modifier = Modifier.padding(bottom = Dimen.mediumAboveHalf)
    )
}

@Composable
fun GlassButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            // ИЗМЕНЕНО: Фон кнопки - Surface с прозрачностью.
            // В светлой теме будет полупрозрачный белый, в темной - полупрозрачный черный.
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            .iosClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun SpecialChip(
    title: String,
    value: String,
    backgroundColor: Color,
    contentColor: Color
) {
    // Оставляем как есть, так как это акцентные цвета (бренд),
    // но можно заменить backgroundColor на MaterialTheme.colorScheme.tertiaryContainer и т.д.
    // если хочешь полную привязку к теме.
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimen.medium))
            .background(backgroundColor)
            .padding(horizontal = Dimen.medium, vertical = Dimen.mediumAboveHalf),
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

@Composable
fun DisclaimerCard() {
    val warningColor = Color(0xFFFF9F0A)
    val backgroundColor = warningColor.copy(alpha = 0.12f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.mediumAboveHalf))
            .background(backgroundColor)
            .padding(Dimen.medium),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Warning",
            tint = warningColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(Dimen.mediumAboveHalf))

        Column {
            Text(
                text = stringResource(R.string.disclaimer_title), // Из ресурсов
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = warningColor
                )
            )
            Spacer(modifier = Modifier.height(Dimen.extraSmall))
            Text(
                text = stringResource(R.string.disclaimer_body), // Из ресурсов
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )
            )
        }
    }
}