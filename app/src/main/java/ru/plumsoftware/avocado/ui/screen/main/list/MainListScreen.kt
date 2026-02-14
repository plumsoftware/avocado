package ru.plumsoftware.avocado.ui.screen.main.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.plumsoftware.avocado.R
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(46.dp))
                IOSTopBar(
                    searchQuery = "",
                    onSearchQueryChange = { it -> },
                    isSearchFocused = false,
                    onFocusChange = { it -> },
                    onFilterClick = {},
                )
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalArrangement = Arrangement.spacedBy(Dimen.medium),
            verticalArrangement = Arrangement.spacedBy(Dimen.medium)
        ) {
            // Фильтры как первый элемент сетки на всю ширину
            item(span = { GridItemSpan(maxLineSpan) }) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.medium),
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

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(R.string.for_breakfast),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        start = Dimen.medium,
                        end = Dimen.medium
                    )
                )
            }
            // Элементы еды
            itemsIndexed(viewModel.recomendedOnBreakfast.value) { index, item ->
                FoodCard(
                    item = item,
                    modifier = Modifier.padding(
                        start = if (index % 2 == 0) Dimen.medium else 0.dp,
                        end = if (index % 2 == 1) Dimen.medium else 0.dp,
                    ),
                    onGetColor = { imageRes, context ->
                        viewModel.getBackgroundColorForFood(imageRes, context)
                    }
                )
            }

            // Нижний отступ
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}