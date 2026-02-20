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
    val (text, icon) = getTimeForFoodResources(time)

    // Стиль iOS чипса (серый фон, темная иконка)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50)) // Полный овал
            .background(MaterialTheme.colorScheme.surfaceContainerHigh) // Адаптивный серый
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
fun VitaminChip(
    text: String,
    @StringRes healthyForRes: Int
) {
    var showTooltip by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Если ресурса нет (0), то подсказки не будет
    val hasInfo = true

    Box {
        // Сам Чип
        Box(
            modifier = Modifier
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant,
                    RoundedCornerShape(Dimen.mediumAboveHalf)
                )
                .clip(RoundedCornerShape(Dimen.mediumAboveHalf))
                // Добавляем клик только если есть инфо
                .then(
                    if (hasInfo) {
                        Modifier.iosClickable { showTooltip = true }
                    } else Modifier
                )
                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        // Подсказка (появляется при showTooltip = true)
        if (hasInfo) {
            IOSPopup(
                text = if (healthyForRes != 0) stringResource(healthyForRes) else "Test",
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
    val hasInfo = true

    Box {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceContainerHigh,
                    RoundedCornerShape(Dimen.mediumAboveHalf)
                )
                .clip(RoundedCornerShape(Dimen.mediumAboveHalf))
                .then(
                    if (hasInfo) {
                        Modifier.iosClickable { showTooltip = true }
                    } else Modifier
                )
                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumAboveHalf)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        if (hasInfo) {
            IOSPopup(
                text = if (healthyForRes != 0) stringResource(healthyForRes) else "Test",
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
fun getTimeForFoodResources(time: TimeForFood): Pair<String, ImageVector> {
    return when (time) {
        TimeForFood.BREAKFAST -> "Лучше на завтрак" to Icons.Default.WbSunny // Или R.drawable.ic_sun
        TimeForFood.LUNCH -> "Идеально на обед" to Icons.Default.Restaurant
        TimeForFood.DINNER -> "Легкий ужин" to Icons.Default.NightsStay // Или R.drawable.ic_moon
        TimeForFood.SNACK -> "Полезный перекус" to Icons.Default.Eco
        TimeForFood.ANY -> "В любое время" to Icons.Default.Schedule
    }
}

@Composable
fun DisclaimerCard() {
    // Apple System Orange (Стандартный цвет предупреждений в iOS)
    // Он хорошо читается и на светлом, и на темном.
    val warningColor = Color(0xFFFF9F0A)

    // Адаптивный фон: Берем цвет предупреждения и делаем его очень прозрачным.
    // На белом это будет пастельно-оранжевый.
    // На черном это будет темно-коричневатый (не слепит).
    val backgroundColor = warningColor.copy(alpha = 0.12f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.mediumAboveHalf)) // Скругление как у уведомлений
            .background(backgroundColor)
            .padding(Dimen.medium),
        verticalAlignment = Alignment.Top // Иконка сверху, если текст в 2 строки
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "AI Warning",
            tint = warningColor,
            modifier = Modifier.size(20.dp) // Аккуратный размер
        )

        Spacer(modifier = Modifier.width(Dimen.mediumAboveHalf))

        Column {
            Text(
                text = "Важно знать",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = warningColor // Заголовок в цвет иконки
                )
            )
            Spacer(modifier = Modifier.height(Dimen.extraSmall))
            Text(
                text = "Содержимое сгенерировано нейросетью. Информация носит справочный характер. Пожалуйста, проконсультируйтесь с врачом.",
                style = MaterialTheme.typography.bodySmall.copy(
                    // Текст берем от темы (Белый в Dark / Черный в Light)
                    // Добавляем прозрачность, чтобы он не был слишком контрастным ("вторичный текст")
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )
            )
        }
    }
}