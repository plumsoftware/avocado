package ru.plumsoftware.avocado.ui.screen.main.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.avocado.data.base.model.food.allFood
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
    private val withFiber_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.withFiber.toMutableList())
    val recomendedOnBreakfast = recomendedOnBreakfast_.asStateFlow()
    val withFiber = withFiber_.asStateFlow()

    val heavyProtein_ =
        MutableStateFlow(ru.plumsoftware.avocado.ui.screen.main.list.elements.food.top7HighProteinFoods.toMutableList())
    val heavyProtein = heavyProtein_.asStateFlow()

    val allFood_ = MutableStateFlow(ru.plumsoftware.avocado.data.base.model.food.allFood)
    val allFood = allFood_.asStateFlow()

    private val colorCache = FoodColorCache()

    fun getBackgroundColorForFood(imageRes: Int, context: Context): Int {
        return colorCache.getBackgroundColor(imageRes, context)
    }

    fun getTextForColorForFood(imageRes: Int, context: Context): HarmoniousColors {
        return colorCache.getHarmoniousColors(imageRes, context)
    }

    fun getLighterColorForFood(imageRes: Int, context: Context): Int {
        return getLightenedColor(getBackgroundColorForFood(imageRes = imageRes, context = context))
    }

    fun updateSelectedFilter(newFilter: Filter) {
        // Обновляем список фильтров
        filters_.update { oldList ->
            oldList.map { filter ->
                if (filter.id == newFilter.id) {
                    // Инвертируем состояние выбранного фильтра
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    // Все остальные сбрасываем
                    filter.copy(isSelected = false)
                }
            } as MutableList<Filter>
        }

        // Обновляем selectedFilter_
        val isNowSelected = !newFilter.isSelected // Инвертируем текущее состояние

        selectedFilter_.update {
            if (isNowSelected) {
                // Фильтр стал выбранным
                newFilter.copy(isSelected = true)
            } else {
                // Фильтр стал невыбранным - возвращаем пустой фильтр
                Filter.empty()
            }
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