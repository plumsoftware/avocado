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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.healthy.Kpfc
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.main.list.getLightenedColor
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.screen.details.elements.IOSPopup
import ru.plumsoftware.avocado.ui.theme.Dimen

// Высота шапки с картинкой
private val HEADER_HEIGHT = 400.dp

@OptIn(ExperimentalLayoutApi::class) // Для FlowRow
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

    // 1. ГЕНЕРАЦИЯ ЦВЕТОВ (Для градиента шапки оставляем логику из картинки, так красиво)
    val baseColorInt = remember(item.imageRes) { onGetColor(item.imageRes, context) }
    val colorStart = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.6f)) }
    val colorEnd = remember(baseColorInt) { Color(getLightenedColor(baseColorInt, 0.2f)) }
    val dominantColor = Color(baseColorInt)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // ИЗМЕНЕНО: Фон из темы
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

            // Лист с информацией
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 800.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface) // ИЗМЕНЕНО: Цвет поверхности (Белый/Темный)
                    .padding(Dimen.large)
            ) {
                // "Ручка" шторки
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)) // ИЗМЕНЕНО
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Заголовок
                Text(
                    text = stringResource(item.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // ИЗМЕНЕНО: Цвет текста
                    )
                )

                // Калорийность
                Text(
                    text = "${item.kpfc_100g.kals} ккал",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // ИЗМЕНЕНО: Вторичный текст
                    ),
                    modifier = Modifier.padding(bottom = Dimen.large)
                )

                // --- КБЖУ БЛОК ---
                MacrosBlock(item.kpfc_100g)

                Spacer(modifier = Modifier.height(32.dp))

                // --- СПЕЦИАЛЬНЫЕ СВОЙСТВА ---
                if (item.kpfc_100g.omega3 >= 0.1 || item.kpfc_100g.fiber >= 0.1) {
                    DetailSectionTitle("Польза на 100г")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumAboveHalf),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (item.kpfc_100g.omega3 >= 0.1) {
                            // Для цветных чипсов лучше использовать контейнеры из темы,
                            // но если нужны специфичные цвета (оранжевый/зеленый), оставим их,
                            // но убедимся, что текст читается.
                            SpecialChip(
                                title = "Омега-3",
                                value = "${item.kpfc_100g.omega3}г",
                                backgroundColor = Color(0xFFFFF3E0), // Можно заменить на tertiaryContainer
                                contentColor = Color(0xFFE65100)
                            )
                        }

                        if (item.kpfc_100g.fiber >= 0.1) {
                            SpecialChip(
                                title = "Клетчатка",
                                value = "${item.kpfc_100g.fiber}г",
                                backgroundColor = Color(0xFFE8F5E9), // Можно заменить на secondaryContainer
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item.vitamins.forEach { vitamin ->
                            VitaminChip(
                                text = stringResource(vitamin.title),
                                healthyForRes = vitamin.healthyFor // Передаем ID описания
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // --- МИНЕРАЛЫ ---
                if (item.minerals.isNotEmpty()) {
                    DetailSectionTitle("Минералы")
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item.minerals.forEach { mineral ->
                            MineralChip(
                                text = stringResource(mineral.title),
                                healthyForRes = mineral.healthyFor // Передаем ID описания
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // --- 3. КНОПКИ НАВИГАЦИИ ---
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
                    tint = MaterialTheme.colorScheme.onSurface // ИЗМЕНЕНО: Цвет иконки от темы
                )
            }

            GlassButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant // ИЗМЕНЕНО
                )
            }
        }
    }
}

// --- ОБНОВЛЕННЫЕ UI ЭЛЕМЕНТЫ ---

@Composable
fun MacrosBlock(kpfc: Kpfc) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            // ИЗМЕНЕНО: Используем Surface Variant (светло-серый в лайт, темно-серый в дарк)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 20.dp, horizontal = Dimen.medium),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Цвета индикаторов (Зеленый/Оранжевый/Синий) лучше оставить хардкодом,
        // так как это стандартные семантические цвета БЖУ, они видны на обоих темах.
        MacroItem(value = "${kpfc.proteins}г", label = "Белки", color = Color(0xFF34C759))
        MacroItem(value = "${kpfc.fats}г", label = "Жиры", color = Color(0xFFFF9500))
        MacroItem(value = "${kpfc.carbohydrates}г", label = "Углев.", color = Color(0xFF007AFF))
    }
}

@Composable
fun MacroItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant // ИЗМЕНЕНО
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f) // ИЗМЕНЕНО
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