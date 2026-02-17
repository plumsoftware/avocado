package ru.plumsoftware.avocado.ui.screen.main.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.allFood
import ru.plumsoftware.avocado.ui.screen.main.list.elements.IOSTopBar
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.FilterItem
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodCard
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun MainListScreen() {
    val viewModel: ListViewModel = viewModel(
        factory = ListViewModel.Companion.ListViewModelFactory()
    )

    val filters by viewModel.filters.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val breakfastItems = viewModel.recomendedOnBreakfast.collectAsState().value
    val fiberItems = viewModel.withFiber.collectAsState().value
    val heavyProtein = viewModel.heavyProtein.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {}
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 0.dp,
                        start = Dimen.medium,
                        end = Dimen.medium
                    ),
                horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
                verticalItemSpacing = Dimen.medium
            ) {

                // Верхний отступ
                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(modifier = Modifier.height(84.dp))
                }

                // Фильтры на всю ширину
                item(span = StaggeredGridItemSpan.FullLine) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            Dimen.medium,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            Dimen.medium,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        filters.forEach { item ->
                            FilterItem(
                                item = item
                            ) {
                                viewModel.updateSelectedFilter(newFilter = item)
                            }
                        }
                    }
                }

                if (selectedFilter.isSelected) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = stringResource(selectedFilter.title),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    // Выбранный фильтр
                    itemsIndexed(viewModel.allFood.value.filter { it.foodType == selectedFilter.foodType }) { index, item ->
                        FoodCard(
                            item = item,
                            modifier = Modifier.fillMaxWidth(),
                            onGetColor = { imageRes, context ->
                                viewModel.getBackgroundColorForFood(imageRes, context)
                            },
                            onGetTextColor = { imageRes, context ->
                                viewModel.getTextForColorForFood(imageRes, context)
                            }
                        )
                    }
                }

                if (!selectedFilter.isSelected) {
                    // Полезно утром
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = stringResource(R.string.for_breakfast),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    // Элементы еды (завтрак)
                    itemsIndexed(breakfastItems) { index, item ->
                        FoodCard(
                            item = item,
                            modifier = Modifier.fillMaxWidth(),
                            onGetColor = { imageRes, context ->
                                viewModel.getBackgroundColorForFood(imageRes, context)
                            },
                            onGetTextColor = { imageRes, context ->
                                viewModel.getTextForColorForFood(imageRes, context)
                            }
                        )
                    }

                    // С клетчаткой
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = stringResource(R.string.with_fiber),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    itemsIndexed(fiberItems) { index, item ->
                        Log.d("TAG", index.toString())
                        FoodCard(
                            item = item,
                            modifier = Modifier.fillMaxWidth(),
                            onGetColor = { imageRes, context ->
                                viewModel.getBackgroundColorForFood(imageRes, context)
                            },
                            onGetTextColor = { imageRes, context ->
                                viewModel.getTextForColorForFood(imageRes, context)
                            }
                        )
                    }

                    // Суперфуды
//                item(span = StaggeredGridItemSpan.FullLine) {
//                    Text(
//                        text = stringResource(R.string.superfoods),
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(
//                            start = Dimen.medium,
//                            end = Dimen.medium
//                        )
//                    )
//                }
//
//                itemsIndexed(superfoodsItems) { _, item ->
//                    FoodCard(
//                        item = item,
//                        modifier = Modifier.fillMaxWidth(),
//                        onGetColor = { imageRes, context ->
//                            viewModel.getBackgroundColorForFood(imageRes, context)
//                        },
//                        onGetTextColor = { imageRes, context ->
//                            viewModel.getTextForColorForFood(imageRes, context)
//                        }
//                    )
//                }

                    // Источники белка (proteins > 15г)
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = stringResource(R.string.protein_sources),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    itemsIndexed(heavyProtein) { _, item ->
                        FoodCard(
                            item = item,
                            modifier = Modifier.fillMaxWidth(),
                            onGetColor = { imageRes, context ->
                                viewModel.getBackgroundColorForFood(imageRes, context)
                            },
                            onGetTextColor = { imageRes, context ->
                                viewModel.getTextForColorForFood(imageRes, context)
                            }
                        )
                    }

                    // Полезные жиры (омега-3 + орехи + авокадо + рыба)
//                item(span = StaggeredGridItemSpan.FullLine) {
//                    Text(
//                        text = stringResource(R.string.healthy_fats),
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(
//                            start = Dimen.medium,
//                            end = Dimen.medium
//                        )
//                    )
//                }
//
//                itemsIndexed(healthyFatsItems) { _, item ->
//                    FoodCard(
//                        item = item,
//                        modifier = Modifier.fillMaxWidth(),
//                        onGetColor = { imageRes, context ->
//                            viewModel.getBackgroundColorForFood(imageRes, context)
//                        },
//                        onGetTextColor = { imageRes, context ->
//                            viewModel.getTextForColorForFood(imageRes, context)
//                        }
//                    )
//                }
                }

                // Нижний отступ
                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            // Градиент и TopBar остаются без изменений
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(48.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background,
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
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