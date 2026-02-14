package ru.plumsoftware.avocado.ui.screen.main.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.avocado.ui.log
import ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.Filter
import ru.plumsoftware.avocado.ui.screen.main.list.elements.food.FoodColorCache

class ListViewModel : ViewModel() {
    private val filters_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.filter.filters.toMutableList())
    val filters = filters_.asStateFlow()

    private val selectedFilter_ = MutableStateFlow(Filter.empty())
    val selectedFilter = selectedFilter_.asStateFlow()

    private val recomendedOnBreakfast_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.recomendedOnBreakfast.toMutableList())
    val recomendedOnBreakfast = recomendedOnBreakfast_.asStateFlow()

    private val colorCache = FoodColorCache()

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun updateSelectedFilter(newFilter: Filter) {
        filters_.update { oldList ->
            val newList = oldList.toMutableList()
            val index = newList.indexOfFirst { it.id == newFilter.id }
            log("index = $index")

            newList.forEachIndexed { i, filter ->
                if (i == index) {
                    if (newList[i].isSelected) {
                        newList[i] = filter.copy(isSelected = false)
                    } else {
                        newList[i] = filter.copy(isSelected = true)
                    }
                } else {
                    newList[i] = filter.copy(isSelected = false)
                }
            }

            log("new list = $newList")
            newList
        }

        selectedFilter_.update {
            newFilter.copy(isSelected = true)
        }
    }

    companion object {
        class ListViewModelFactory() :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ListViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}