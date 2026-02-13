package ru.plumsoftware.avocado.ui.screen.main.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.ui.screen.main.elements.filter.FilterItem
import ru.plumsoftware.avocado.ui.screen.main.elements.filter.filters
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun MainListScreen() {
    val filters = filters

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.medium),
        horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(Dimen.mediumHalf, alignment = Alignment.CenterVertically)
    ) {
        filters.forEach { item ->
            FilterItem(
                item = item
            ) {

            }
        }
    }
}