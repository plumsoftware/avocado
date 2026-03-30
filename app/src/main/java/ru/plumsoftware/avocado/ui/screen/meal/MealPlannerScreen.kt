package ru.plumsoftware.avocado.ui.screen.meal

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import ru.plumsoftware.avocado.data.meal.MealPlanRepository
import ru.plumsoftware.avocado.data.meal.MealType
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.MainViewModel
import ru.plumsoftware.avocado.ui.screen.main.receipt.RecipesViewModel
import ru.plumsoftware.avocado.ui.screen.meal.elements.DailySummaryCard
import ru.plumsoftware.avocado.ui.screen.meal.elements.DailyTotals
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun MealPlannerScreen(
    navController: NavHostController,
    userPreferencesRepository: UserPreferencesRepository,
    shoppingRepository: ShoppingRepository,
    mealPlanRepository: MealPlanRepository
) {
    val viewModel: MealPlannerViewModel = viewModel(factory = MealPlannerViewModel.Factory(repo = mealPlanRepository))
    val recipesViewModel: RecipesViewModel =
        viewModel(factory = RecipesViewModel.Factory(userPrefsRepo = userPreferencesRepository, shoppingRepository = shoppingRepository))

    val selectedDate by viewModel.selectedDate.collectAsState()
    val weekDays by viewModel.weekDays.collectAsState()
    val dailyPlan by viewModel.dailyPlan.collectAsState()
    val allRecipes by recipesViewModel.recipeList.collectAsState()
    var showRecipeSelector by remember { mutableStateOf(false) }
    var selectedMealTypeForAdd by remember { mutableStateOf<MealType?>(null) }
    var showSwipeHint by rememberSaveable { mutableStateOf(true) }

    val dailyTotals = remember(dailyPlan) {
        var kals = 0
        var proteins = 0.0
        var fats = 0.0
        var carbs = 0.0

        dailyPlan.forEach { mealPlan ->
            // Ищем рецепт
            val recipe = recipesViewModel.getReceiptById(mealPlan.recipeId)

            if (recipe != null) {
                // Калории берем напрямую из рецепта
                kals += recipe.calories

                // Получаем список ингредиентов (List<Food>) для этого рецепта
                val ingredients = recipesViewModel.getIngredients(recipe.relatedFood)

                // Суммируем БЖУ всех ингредиентов
                ingredients.forEach { food ->
                    proteins += food.kpfc_100g.proteins
                    fats += food.kpfc_100g.fats
                    carbs += food.kpfc_100g.carbohydrates
                }
            }
        }
        DailyTotals(kals, proteins, fats, carbs)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimen.medium),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // --- 1. ЗАГОЛОВОК ---
            item {
                Spacer(modifier = Modifier.height(Dimen.medium))
                Text(
                    text = stringResource(R.string.planner_title),
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.planner_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = Dimen.large)
                )
            }

            // --- 2. КАЛЕНДАРЬ ---
            item {
                // Создаем форматтер для сравнения дат
                val sdf = remember { java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(weekDays) { date ->
                        CalendarDayItem(
                            date = date,
                            isSelected = sdf.format(date) == sdf.format(selectedDate),
                            onClick = { viewModel.selectDate(date) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Dimen.extraLarge))
            }

            if (showSwipeHint && dailyPlan.isNotEmpty()) {
                item {
                    AnimatedVisibility(
                        visible = showSwipeHint,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Dimen.large) // Отступ до слотов питания
                                .clip(RoundedCornerShape(Dimen.mediumHalf))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.planner_swipe_hint),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(R.string.cd_close),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(16.dp)
                                    .iosClickable { showSwipeHint = false }
                            )
                        }
                    }
                }
            } else if (dailyPlan.isEmpty()) {
                // Если план пуст и баннера нет, просто добавим отступ, чтобы верстка не прыгала
                item { Spacer(modifier = Modifier.height(Dimen.medium)) }
            }

            item {
                DailySummaryCard(totals = dailyTotals)
                Spacer(modifier = Modifier.height(Dimen.extraLarge))
            }

            // --- 3. СЛОТЫ ПИТАНИЯ ---
            val mealTypes = listOf(
                MealType.BREAKFAST to R.string.meal_breakfast,
                MealType.LUNCH to R.string.meal_lunch,
                MealType.DINNER to R.string.meal_dinner,
                MealType.SNACK to R.string.meal_snack
            )

            mealTypes.forEach { (type, titleRes) ->
                item {
                    Text(
                        text = stringResource(titleRes),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = Dimen.mediumHalf)
                    )
                }

                val mealsForType = dailyPlan.filter { it.mealType == type.name }

                if (mealsForType.isEmpty()) {
                    item {
                        EmptyMealSlot(onClick = {
                            selectedMealTypeForAdd = type
                            showRecipeSelector = true
                        })
                        Spacer(modifier = Modifier.height(Dimen.large))
                    }
                } else {
                    items(
                        items = mealsForType,
                        key = { it.id }
                    ) { mealPlan ->
                        val receipt = recipesViewModel.getReceiptById(mealPlan.recipeId)

                        if (receipt != null) {
                            MealPlanReceiptCard(
                                receipt = receipt,
                                onDelete = { viewModel.removeMeal(mealPlan.id) },
                                onClick = { navController.navigate(AppDestination.ReceiptDetailRoute(receipt.id)) }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(Dimen.large)) }
                }
            }
        }

        // --- 4. ШТОРКА ВЫБОРА РЕЦЕПТА ---
        if (showRecipeSelector && selectedMealTypeForAdd != null) {
            RecipeSelectionSheet(
                recipes = allRecipes,
                onDismiss = {
                    showRecipeSelector = false
                    selectedMealTypeForAdd = null
                },
                onRecipeSelected = { recipeId ->
                    viewModel.addMeal(selectedMealTypeForAdd!!, recipeId)
                    showRecipeSelector = false
                    selectedMealTypeForAdd = null
                }
            )
        }
    }
}

// =====================================
// КОМПОНЕНТЫ УПРАВЛЕНИЯ UI
// =====================================

@Composable
fun CalendarDayItem(date: Date, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor by animateColorAsState(if (isSelected) IOSGreen else Color.Transparent, label = "bg")
    val textColor by animateColorAsState(if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface, label = "txt")

    // Получаем день недели ("Пн", "Вт") с помощью SimpleDateFormat
    val sdfDayOfWeek = remember { SimpleDateFormat("E", Locale("ru", "RU")) }
    val dayOfWeek = sdfDayOfWeek.format(date).replaceFirstChar { it.uppercase() }

    // Получаем число месяца ("24")
    val sdfDayOfMonth = remember { SimpleDateFormat("d", Locale.getDefault()) }
    val dayOfMonth = sdfDayOfMonth.format(date)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.iosClickable { onClick() }
    ) {
        Text(
            text = dayOfWeek,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayOfMonth,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = textColor
            )
        }
    }
}

// Пунктирная кнопка (iOS Style Empty State)
@Composable
fun EmptyMealSlot(onClick: () -> Unit) {
    val strokeColor = MaterialTheme.colorScheme.outlineVariant
    val stroke = Stroke(
        width = 4f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(Dimen.medium))
            .drawBehind {
                drawRoundRect(color = strokeColor, style = stroke, cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx()))
            }
            .iosClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Add, contentDescription = null, tint = IOSGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.planner_add_recipe),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = IOSGreen
            )
        }
    }
}

// Горизонтальная карточка рецепта со свайпом для удаления
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanReceiptCard(
    receipt: TypicalReceipt,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) Color(0xFFFF3B30) else Color(0xFFFF3B30).copy(alpha = 0.5f))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = Dimen.mediumHalf)
                    .clip(RoundedCornerShape(Dimen.medium))
                    .background(color)
                    .padding(end = Dimen.medium),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = stringResource(R.string.cd_delete), tint = Color.White)
            }
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimen.mediumHalf)
                    .clip(RoundedCornerShape(Dimen.medium))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .iosClickable { onClick() } // Клик по самой карточке ведет на рецепт
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1. Картинка рецепта (квадратная)
                Image(
                    painter = painterResource(receipt.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.width(Dimen.medium))

                // 2. Информация (Текст и КБЖУ)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(receipt.titleRes),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.format_kcal, receipt.calories), // Из ресурсов!
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(Dimen.medium))
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.format_minutes, receipt.timeMinutes), // Из ресурсов!
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // 3. 🔥 КНОПКА УДАЛЕНИЯ (Крестик)
//                Box(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clip(CircleShape)
//                        .background(MaterialTheme.colorScheme.surfaceVariant)
//                        .iosClickable { onDelete() }, // Вызываем удаление при клике именно на крестик
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Rounded.Close,
//                        contentDescription = stringResource(R.string.cd_delete),
//                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.size(16.dp)
//                    )
//                }

                // Небольшой отступ от правого края
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    )
}


// ==========================================
// НОВЫЙ КОМПОНЕНТ: ШТОРКА ВЫБОРА РЕЦЕПТОВ
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSelectionSheet(
    recipes: List<TypicalReceipt>, // Твоя модель рецепта
    onDismiss: () -> Unit,
    onRecipeSelected: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = Dimen.medium, bottom = Dimen.mediumHalf)
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            )
        },
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f) // Шторка на 80% высоты экрана
                .padding(horizontal = Dimen.medium)
        ) {
            // Заголовок шторки
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimen.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.planner_choose_recipe),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .iosClickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Список рецептов для выбора
            LazyColumn(
                contentPadding = PaddingValues(bottom = Dimen.extraLarge),
                verticalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)
            ) {
                items(recipes, key = { it.id }) { recipe ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Dimen.medium))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .iosClickable { onRecipeSelected(recipe.id) } // Клик = выбор
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(recipe.imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.width(Dimen.medium))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(recipe.titleRes),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${recipe.calories} ккал",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(Dimen.medium))
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${recipe.timeMinutes} мин",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}