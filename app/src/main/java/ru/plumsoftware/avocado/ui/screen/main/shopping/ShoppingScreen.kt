package ru.plumsoftware.avocado.ui.screen.main.shopping

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.plumsoftware.avocado.data.shopping.ShoppingItemEntity
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.FoodType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShoppingScreen(
    navController: NavController,
    shoppingRepository: ShoppingRepository
) {
    val viewModel: ShoppingViewModel =
        viewModel(factory = ShoppingViewModel.Factory(repo = shoppingRepository))
    val items by viewModel.shoppingList.collectAsState()

    // Сгруппированный список
    val groupedItems by viewModel.groupedShoppingList.collectAsState()

    var showSwipeHint by rememberSaveable { mutableStateOf(true) }
    val color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.shopping_cart_title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    if (items.any { it.isChecked }) {
                        TextButton(onClick = { viewModel.clearChecked() }) {
                            Text(
                                text = stringResource(R.string.shopping_btn_clear),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color(
                                        0xFF007AFF
                                    )
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimen.medium)
        ) {
            Text(
                text = stringResource(R.string.shopping_cart_title),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimen.large)
            )

            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.shopping_empty),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(modifier = Modifier.height(Dimen.medium))
                        Text(
                            text = stringResource(R.string.shopping_cart_empty),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {

                AnimatedVisibility(
                    visible = showSwipeHint,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimen.medium)
                            .clip(RoundedCornerShape(Dimen.mediumHalf))
                            .background(color)
                            .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Иконка стрелочки влево (намек на жест)
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(Dimen.medium)
                            )
                            Spacer(modifier = Modifier.width(Dimen.mediumHalf))
                            Text(
                                text = stringResource(R.string.shopping_swipe_hint),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Кнопка закрыть (крестик)
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(R.string.cd_close),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(16.dp)
                                .iosClickable { showSwipeHint = false } // Закрываем по клику
                        )
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    groupedItems.forEach { (type, categoryItems) ->
                        // 1. Заголовок категории (Sticky Header)
                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f))
                                    .padding(vertical = Dimen.mediumHalf)
                            ) {
                                Text(
                                    text = getFoodTypeTitle(type),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // 2. Элементы внутри категории
                        itemsIndexed(
                            items = categoryItems,
                            key = { _, item -> item.foodId }
                        ) { index, item ->
                            SwipeToDeleteItem(
                                item = item,
                                onCheck = { isChecked ->
                                    viewModel.toggleCheck(
                                        item.foodId,
                                        isChecked
                                    )
                                },
                                onDelete = { viewModel.deleteItem(item.foodId) },
                                isFirst = index == 0,
                                isLast = index == categoryItems.lastIndex,
                            )
                        }

                        // 3. Отступ между категориями
                        item { Spacer(modifier = Modifier.height(Dimen.medium)) }
                    }
                }
            }
        }
    }
}

// --- IOS SWIPE-TO-DELETE + CHECKBOX ITEM ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(
    item: ShoppingItemEntity,
    isFirst: Boolean, // <--- НОВЫЙ ПАРАМЕТР
    isLast: Boolean,  // <--- НОВЫЙ ПАРАМЕТР
    onCheck: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    // 1. Динамическое скругление углов (iOS Style)
    // Если элемент единственный в списке, он будет скруглен со всех сторон (isFirst = true, isLast = true)
    val cornerRadius = 14.dp
    val shape = RoundedCornerShape(
        topStart = if (isFirst) cornerRadius else 0.dp,
        topEnd = if (isFirst) cornerRadius else 0.dp,
        bottomStart = if (isLast) cornerRadius else 0.dp,
        bottomEnd = if (isLast) cornerRadius else 0.dp
    )

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
        enableDismissFromStartToEnd = false, // Свайп только влево
        // 2. Применяем клиппинг КО ВСЕМУ БЛОКУ, чтобы красный фон тоже скруглялся
        modifier = Modifier.clip(shape),
        backgroundContent = {
            val color by animateColorAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) Color(0xFFFF3B30) else Color.Transparent
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color) // Фон обрежется благодаря Modifier.clip(shape) выше
                    .padding(end = Dimen.medium),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = "Удалить", tint = Color.White)
            }
        },
        content = {
            // 3. Главный контейнер карточки
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface) // Белый/Темно-серый фон
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .iosClickable { onCheck(!item.isChecked) }
                        .padding(vertical = Dimen.mediumHalf, horizontal = Dimen.mediumHalf),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Чекбокс
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (item.isChecked) IOSGreen else Color.Transparent)
                            .border(
                                width = 2.dp,
                                color = if (item.isChecked) IOSGreen else MaterialTheme.colorScheme.outlineVariant,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.isChecked) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(Dimen.medium))

                    // Мини-картинка
                    Image(
                        painter = painterResource(item.imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(Dimen.medium))

                    // Название
                    Text(
                        text = stringResource(item.titleRes),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = if (item.isChecked) MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.5f
                            ) else MaterialTheme.colorScheme.onSurface,
                            textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                }

                // 4. Разделитель (iOS Style: не доходит до левого края, не показывается у последнего элемента)
                if (!isLast) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 76.dp), // Отступ равен Чекбокс + Картинка + Спейсеры
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    )
                }
            }
        }
    )
}

@Composable
fun getFoodTypeTitle(type: FoodType): String {
    return when (type) {
        FoodType.FRUIT -> stringResource(R.string.type_fruit)
        FoodType.VEGETABLE -> stringResource(R.string.type_vegetable)
        FoodType.MEAT -> stringResource(R.string.type_meat)
        FoodType.FISH -> stringResource(R.string.type_fish)
        FoodType.NUT -> stringResource(R.string.type_nut)
        FoodType.STRAWBERRY -> stringResource(R.string.type_berry)
        FoodType.MUSHROOM -> stringResource(R.string.type_mushroom)
        FoodType.SEAFOOD -> stringResource(R.string.type_seafood)
        else -> type.name // Запасной вариант
    }
}