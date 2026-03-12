package ru.plumsoftware.avocado.ui.screen.main.receipt.details

import android.content.Context
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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.details.DisclaimerCard
import ru.plumsoftware.avocado.ui.screen.details.GlassButton
import ru.plumsoftware.avocado.ui.screen.main.receipt.RecipesViewModel
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

private val HEADER_HEIGHT = 380.dp

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class) // Нужен для ModalBottomSheet
@Composable
fun ReceiptDetailScreen(
    receiptId: String,
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository
) {
    val viewModel: RecipesViewModel =
        viewModel(factory = RecipesViewModel.Factory(userPrefsRepo = userPreferencesRepository))
    val receipt = remember { viewModel.getReceiptById(receiptId) }

    if (receipt == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Рецепт не найден", color = MaterialTheme.colorScheme.onBackground)
        }
        return
    }

    val ingredients = remember { viewModel.getIngredients(receipt.relatedFood) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val rawInstructions = stringResource(receipt.receiptText)
    val steps = remember(rawInstructions) {
        rawInstructions.split("\n").filter { it.isNotBlank() }
    }

    // Состояние для отображения режима готовки (шторки)
    var showCookingMode by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. PARALLAX HEADER ---
        // ... (Код хедера остается без изменений) ...
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

                Spacer(modifier = Modifier.height(60.dp))

                // Заголовок
                Text(
                    text = stringResource(receipt.titleRes),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                // Краткое описание
                Text(
                    text = stringResource(receipt.descRes),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                // --- МЕТА ДАННЫЕ ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ReceiptMetaBig(Icons.Default.Schedule, "${receipt.timeMinutes} мин", "Время")
                    ReceiptMetaBig(Icons.Default.LocalFireDepartment, "${receipt.calories}", "Ккал")
                    val diffText = when (receipt.difficulty) {
                        1 -> "Легко"
                        2 -> "Средне"
                        else -> "Сложно"
                    }
                    ReceiptMetaBig(Icons.Default.Bolt, diffText, "Уровень")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- КНОПКА "НАЧАТЬ ГОТОВКУ" (НОВАЯ) ---
                Button(
                    onClick = { showCookingMode = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.buttonHeight),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = IOSGreen, contentColor = Color.White)
                ) {
                    Text(
                        "Начать готовку",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- ИНГРЕДИЕНТЫ ---
                if (ingredients.isNotEmpty()) {
                    Text(
                        text = "Ингредиенты",
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
                            IngredientItem(food) {
                                navController.navigate(AppDestination.DetailedScreen(foodId = food.id))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // --- ПРИГОТОВЛЕНИЕ ---
                Text(
                    text = "Приготовление",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
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

        // --- 3. BACK BUTTON ---
        Box(
            modifier = Modifier.padding(top = 48.dp, start = 16.dp)
        ) {
            GlassButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    // --- МОДАЛЬНАЯ ШТОРКА ГОТОВКИ ---
    if (showCookingMode) {
        CookingModeSheet(
            title = stringResource(receipt.titleRes),
            ingredients = ingredients,
            steps = steps,
            onDismiss = { showCookingMode = false }
        )
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
            // ИСПРАВЛЕНО: surfaceVariant лучше работает в Dark Mode, чем surfaceContainerLow
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
                color = MaterialTheme.colorScheme.onSurface // ИСПРАВЛЕНО: Явный цвет
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f) // ИСПРАВЛЕНО
            )
        )
    }
}

@Composable
fun IngredientItem(food: Food, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .iosClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant) // Серый кружок для контраста
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(food.titleRes),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface // ИСПРАВЛЕНО: Цвет названия
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
                color = MaterialTheme.colorScheme.onSurface // ИСПРАВЛЕНО: Явный цвет шагов
            )
        )
    }
}