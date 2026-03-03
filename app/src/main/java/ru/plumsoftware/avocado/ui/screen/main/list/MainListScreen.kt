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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun MainListScreen(
    navController: NavHostController,
    favoritesRepository: FavoritesRepository,
    userPreferencesRepository: UserPreferencesRepository
) {
    val context = LocalContext.current

    // Используем нашу новую Фабрику
    val viewModel: ListViewModel = viewModel(
        factory = ListViewModel.Companion.Factory(
            context = context.applicationContext,
            favoritesRepo = favoritesRepository,
            userPrefsRepo = userPreferencesRepository
        )
    )

    // --- ПОДПИСКИ ---
    val filters by viewModel.filters.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val favorites by viewModel.favoriteIds.collectAsState()
    val sections by viewModel.homeSections.collectAsState()

    // ПОИСК
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    // Состояние фокуса поиска (для анимации кнопки "Отмена")
    var isSearchFocused by remember { mutableStateOf(false) }

    // Активен ли режим поиска? (Если есть текст)
    val isSearching = searchQuery.isNotEmpty()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {}
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimen.medium),
                verticalArrangement = Arrangement.spacedBy(Dimen.medium),
                horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {

                // Отступ сверху
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(84.dp))
                }

                // --- ЛОГИКА ОТОБРАЖЕНИЯ ---

                if (isSearching) {
                    // --- РЕЖИМ ПОИСКА ---

                    if (searchResults.isEmpty()) {
                        // Ничего не найдено
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Ничего не найдено",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        // Результаты поиска
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            SectionTitle(title = "Результаты поиска")
                        }

                        itemsIndexed(searchResults) { _, item ->
                            FoodCardItem(
                                item = item,
                                isFavorite = favorites.contains(item.id),
                                viewModel = viewModel,
                                onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = item.id)) }
                            )
                        }
                    }

                } else {
                    // --- ОБЫЧНЫЙ РЕЖИМ (Фильтры + Главная) ---

                    // Фильтры
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                            contentPadding = PaddingValues(horizontal = Dimen.extraSmall)
                        ) {
                            items(filters) { item ->
                                FilterItem(item = item) {
                                    viewModel.updateSelectedFilter(newFilter = item)
                                }
                            }
                        }
                    }

                    if (selectedFilter.isSelected) {
                        // Выбран фильтр
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            SectionTitle(title = stringResource(selectedFilter.title))
                        }
                        itemsIndexed(
                            items = viewModel.allFood.value.filter { it.foodType == selectedFilter.foodType }
                        ) { _, item ->
                            FoodCardItem(
                                item = item,
                                isFavorite = favorites.contains(item.id),
                                viewModel = viewModel,
                                onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = item.id)) }
                            )
                        }
                    } else {
                        // Главная страница (Секции)
                        sections.forEach { section ->
                            if (section.items.isNotEmpty()) {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    SectionTitle(title = stringResource(section.titleRes))
                                }
                                itemsIndexed(section.items) { _, item ->
                                    FoodCardItem(
                                        item = item,
                                        isFavorite = favorites.contains(item.id),
                                        viewModel = viewModel,
                                        onFoodClick = { navController.navigate(AppDestination.DetailedScreen(foodId = item.id)) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // --- TOP BAR ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(90.dp)
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
                    searchQuery = searchQuery,
                    onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                    isSearchFocused = isSearchFocused,
                    onFocusChange = { isSearchFocused = it },
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