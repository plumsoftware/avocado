package ru.plumsoftware.avocado.ui.screen.main.receipt.details

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CookingModeSheet(
    title: String,
    ingredients: List<Food>,
    steps: List<String>,
    onDismiss: () -> Unit
) {
    // Не даем экрану погаснуть
    KeepScreenOn()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    // +1 шаг для стартового экрана с ингредиентами
    val pagerState = rememberPagerState(pageCount = { steps.size + 1 })

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.92f

    // 1. Инициализируем TTS
    val tts = rememberTextToSpeech()

    // 2. Останавливаем голос при перелистывании слайда!
    LaunchedEffect(pagerState.currentPage) {
        tts?.stop()
    }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimen.buttonHeight)
            .height(sheetHeight),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = Dimen.medium)
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            )
        },
        tonalElevation = 0.dp,
        scrimColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimen.medium)
                .height(sheetHeight)
        ) {
            // --- HEADER ШТОРКИ ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.large),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Текст прогресса
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    val progressText = if (pagerState.currentPage == 0) "Ингредиенты"
                    else "Шаг ${pagerState.currentPage} из ${steps.size}"
                    Text(
                        text = progressText,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Кнопка закрыть (iOS Style)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .iosClickable {
                            scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimen.medium))

            // Прогресс бар
            LinearProgressIndicator(
                progress = { pagerState.currentPage / steps.size.toFloat() }, // От 0 до 1
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.extraSmall),
                color = IOSGreen,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            // --- КОНТЕНТ (СВАЙПЕР) ---
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                if (page == 0) {
                    // Нулевой слайд: Проверь ингредиенты
                    CookingIngredientsSlide(ingredients = ingredients, tts = tts)
                } else {
                    // Шаги (page - 1, потому что 0 это ингредиенты)
                    CookingStepSlide(stepText = steps[page - 1], tts = tts)
                }
            }

            // --- BOTTOM BUTTONS ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.large),
                horizontalArrangement = Arrangement.spacedBy(Dimen.medium)
            ) {
                // Кнопка Назад
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage > 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    },
                    enabled = pagerState.currentPage > 0,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Text("Назад", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }

                // Кнопка Далее / Готово
                val isLast = pagerState.currentPage == steps.size
                Button(
                    onClick = {
                        scope.launch {
                            if (isLast) {
                                sheetState.hide()
                                onDismiss()
                            } else {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IOSGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isLast) "Приятного!" else "Далее",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Слайд 0: Список того, что нужно подготовить
@Composable
fun CookingIngredientsSlide(
    ingredients: List<Food>,
    tts: TextToSpeech?
) {
    val context = LocalContext.current

    // Формируем текст для озвучки ингредиентов
    val textToRead = remember(ingredients) {
        val names = ingredients.joinToString(", ") { context.getString(it.titleRes) }
        "Вам понадобятся следующие ингредиенты: $names"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.cooking_ingredients_check),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔊 Кнопка озвучки
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(IOSGreen.copy(alpha = 0.15f))
                .iosClickable {
                    tts?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                contentDescription = "Прочитать",
                tint = IOSGreen
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Показываем ингредиенты
        // Используем LazyColumn, чтобы список скроллился, если ингредиентов много
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ingredients) { food ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(food.imageRes),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(food.titleRes),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CookingStepSlide(
    stepText: String,
    tts: TextToSpeech?
) {
    val cleanText = stepText.replaceFirst(Regex("^\\d+\\.\\s*"), "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = cleanText,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 32.sp,
                lineHeight = 44.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 🔊 Большая кнопка озвучки под текстом шага
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(IOSGreen.copy(alpha = 0.15f))
                .iosClickable {
                    tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, null)
                }
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                contentDescription = "Прочитать",
                tint = IOSGreen
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Озвучить шаг",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = IOSGreen
                )
            )
        }
    }
}