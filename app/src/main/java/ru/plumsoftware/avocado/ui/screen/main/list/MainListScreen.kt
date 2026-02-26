package ru.plumsoftware.avocado.ui.screen.main.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.list.elements.IOSTopBar
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.FilterItem
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodCard
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun MainListScreen(navController: NavHostController, favoritesRepository: FavoritesRepository, userPreferencesRepository: UserPreferencesRepository) {
    val context = LocalContext.current

    val viewModel: ListViewModel = viewModel(
        factory = ListViewModel.Companion.ListViewModelFactory(
            favoritesRepository = favoritesRepository,
            userPreferencesRepository = userPreferencesRepository
        )
    )

    val filters by viewModel.filters.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val favorites by viewModel.favoriteIds.collectAsState()

    // Списки продуктов
    val breakfastItems = viewModel.recomendedOnBreakfast.collectAsState().value
    val fiberItems = viewModel.withFiber.collectAsState().value
    val heavyProtein = viewModel.heavyProtein.collectAsState().value
    val healthyFatsItems = viewModel.healthyFatsItems.collectAsState().value
    val sections by viewModel.homeSections.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {}
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            // --- ИЗМЕНЕНИЕ: ИСПОЛЬЗУЕМ ОБЫЧНУЮ СЕТКУ ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Строгая сетка в 2 колонки
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimen.medium), // Отступы сразу в Grid
                // В обычной сетке verticalArrangement управляет отступами между строками
                verticalArrangement = Arrangement.spacedBy(Dimen.medium),
                horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                contentPadding = PaddingValues(bottom = 100.dp) // Нижний отступ для контента
            ) {

                // 1. ВЕРХНИЙ ОТСТУП (под TopBar)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(84.dp))
                }

                // 2. ФИЛЬТРЫ (Горизонтальный скролл)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    // Лучше использовать LazyRow для скролла фильтров
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                        // contentPadding добавляет отступы, чтобы элементы не прилипали к краям при скролле
                        contentPadding = PaddingValues(horizontal = Dimen.extraSmall)
                    ) {
                        items(filters) { item ->
                            FilterItem(
                                item = item
                            ) {
                                viewModel.updateSelectedFilter(newFilter = item)
                            }
                        }
                    }
                }

                // ЛОГИКА ОТОБРАЖЕНИЯ (Выбран фильтр или Главная)

                if (selectedFilter.isSelected) {
                    // Заголовок фильтра
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionTitle(title = stringResource(selectedFilter.title))
                    }

                    // Элементы фильтра
                    itemsIndexed(
                        items = viewModel.allFood.value.filter { it.foodType == selectedFilter.foodType }
                    ) { _, item ->
                        FoodCardItem(
                            item = item,
                            isFavorite = favorites.contains(item.id),
                            viewModel = viewModel,
                            onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = it.id)) }
                        )
                    }
                } else {
                    // --- ГЛАВНАЯ СТРАНИЦА ---

                    sections.forEach { section ->
                        // Рендерим секцию только если в ней есть продукты
                        if (section.items.isNotEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                SectionTitle(title = stringResource(section.titleRes))
                            }

                            itemsIndexed(section.items) { _, item ->
                                FoodCardItem(
                                    item = item,
                                    isFavorite = favorites.contains(item.id),
                                    viewModel = viewModel,
                                    onFoodClick = {
                                        navController.navigate(AppDestination.DetailedScreen(foodId = it.id))
                                    }
                                )
                            }
                        }
                    }

//                    // Секция: Завтрак
//                    item(span = { GridItemSpan(maxLineSpan) }) {
//                        SectionTitle(title = stringResource(R.string.for_breakfast))
//                    }
//                    itemsIndexed(breakfastItems) { _, item ->
//                        FoodCardItem(
//                            item = item,
//                            isFavorite = favorites.contains(item.id),
//                            viewModel = viewModel,
//                            onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = it.id)) }
//                        )
//                    }
//
//                    // Секция: Клетчатка
//                    item(span = { GridItemSpan(maxLineSpan) }) {
//                        SectionTitle(title = stringResource(R.string.with_fiber))
//                    }
//                    itemsIndexed(fiberItems) { _, item ->
//                        FoodCardItem(
//                            item = item,
//                            isFavorite = favorites.contains(item.id),
//                            viewModel = viewModel,
//                            onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = it.id)) }
//                        )
//                    }
//
//                    // Секция: Белок
//                    item(span = { GridItemSpan(maxLineSpan) }) {
//                        SectionTitle(title = stringResource(R.string.protein_sources))
//                    }
//                    itemsIndexed(heavyProtein) { _, item ->
//                        FoodCardItem(
//                            item = item,
//                            isFavorite = favorites.contains(item.id),
//                            viewModel = viewModel,
//                            onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = it.id)) }
//                        )
//                    }
//
//                    // Секция: Жиры
//                    item(span = { GridItemSpan(maxLineSpan) }) {
//                        SectionTitle(title = stringResource(R.string.healthy_fats))
//                    }
//                    itemsIndexed(healthyFatsItems) { _, item ->
//                        FoodCardItem(
//                            item = item,
//                            isFavorite = favorites.contains(item.id),
//                            viewModel = viewModel,
//                            onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = it.id)) }
//                        )
//                    }
                }
            }

            // --- ВЕРХНЯЯ ПАНЕЛЬ (Top Bar) ---
            // Остается без изменений, так как лежит поверх списка в Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(90.dp) // Чуть увеличим зону фона
                    // Добавляем blur (размытие фона списка под хедером) - iOS Style
                    // Работает на Android 12+, на старых просто будет прозрачным
                    // .blur(20.dp) // Раскомментируй, если minSdk >= 31
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                IOSTopBar(
                    searchQuery = "",
                    onSearchQueryChange = { },
                    isSearchFocused = false,
                    onFocusChange = { },
                    onFilterClick = {},
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
fun FoodCardItem(
    item: Food,
    isFavorite: Boolean,
    viewModel: ListViewModel,
    onFoodClick: (Food) -> Unit
) {
    FoodCard(
        item = item,
        isFavorite = isFavorite,
        modifier = Modifier.fillMaxWidth(),
        onGetColor = { imageRes, context ->
            viewModel.getBackgroundColorForFood(imageRes, context)
        },
        onLikeClick = {
            viewModel.onLikeClick(item.id) // Обработка лайка
        },
        onFoodClick = { item ->
            onFoodClick(item)
        }
    )
}