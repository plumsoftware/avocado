package ru.plumsoftware.avocado.ui.screen.main.favorite

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.data.favorite.FavoritesRepository
import ru.plumsoftware.avocado.ui.screen.main.list.elements.IOSTopBar
import ru.plumsoftware.avocado.ui.theme.Dimen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodCard

@Composable
fun FavoriteScreen(favoritesRepository: FavoritesRepository, navController: NavHostController) {
    val viewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModel.Companion.FavoriteViewModelFactory(favoritesRepository)
    )

    // Подписываемся на список избранного
    val favoriteItems by viewModel.favoriteFood.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {}
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            if (favoriteItems.isEmpty()) {
                // --- ПУСТОЙ ЭКРАН (EMPTY STATE) ---
                EmptyFavoritesState(modifier = Modifier.align(Alignment.Center))
            } else {
                // --- СПИСОК ИЗБРАННОГО ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = Dimen.medium),
                    verticalArrangement = Arrangement.spacedBy(Dimen.medium),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    // Отступ под TopBar
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier.height(84.dp))
                    }

                    // Заголовок (Опционально, но в iOS обычно пишут "Избранное")
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(R.string.fav), // "Избранное"
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Сами карточки
                    items(favoriteItems) { item ->
                        FoodCard(
                            item = item,
                            isFavorite = true,
                            modifier = Modifier.fillMaxWidth(),
                            onGetColor = { res, ctx ->
                                viewModel.getBackgroundColorForFood(
                                    res,
                                    ctx
                                )
                            },
                            onLikeClick = {
                                viewModel.onLikeClick(item.id)
                            },
                            onFoodClick = {
                                navController.navigate(AppDestination.DetailedScreen(foodId = item.id))
                            }
                        )
                    }
                }
            }

            // --- TOP BAR (Как на главной) ---
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
                // Можно оставить поиск, если хочешь искать внутри избранного
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

// Красивая заглушка, если пусто
@Composable
fun EmptyFavoritesState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.nothing),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(Dimen.medium))

        Text(
            text = stringResource(R.string.favorites_empty_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Dimen.mediumHalf))

        Text(
            text = stringResource(R.string.favorites_empty_desc),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}