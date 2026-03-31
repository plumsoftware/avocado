package ru.plumsoftware.avocado.ui.screen.main.meal

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.meal.MealPlanEntity
import ru.plumsoftware.avocado.data.meal.MealPlanRepository
import ru.plumsoftware.avocado.data.meal.MealType
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.data.water.WaterRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MealPlannerViewModel(
    private val repository: MealPlanRepository,
    private val waterRepository: WaterRepository
) : ViewModel() {

    // Форматтер для БД: "2026-03-24"
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // 1. Управление датой (теперь используем java.util.Date)
    private val _selectedDate = MutableStateFlow(Date())
    val selectedDate = _selectedDate.asStateFlow()

    // 2. Генерация недели (+-3 дня от сегодня)
    val weekDays: StateFlow<List<Date>> = _selectedDate.map {
        (-3..3).map { offset ->
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, offset)
            calendar.time
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- 🔥 ВОДА ПО ВЫБРАННОЙ ДАТЕ ---
    @OptIn(ExperimentalCoroutinesApi::class)
    val waterIntake: StateFlow<Int> = _selectedDate
        .flatMapLatest { date ->
            // При смене дня в календаре, автоматически подгружается вода за этот день!
            waterRepository.getWaterForDate(dateFormatter.format(date))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun addWater(amountMl: Int) {
        viewModelScope.launch {
            val currentDateStr = dateFormatter.format(_selectedDate.value)
            val currentWater = waterIntake.value
            waterRepository.addWater(currentDateStr, currentWater, amountMl)
        }
    }

    // 3. Подгрузка плана на ВЫБРАННЫЙ день
    @OptIn(ExperimentalCoroutinesApi::class)
    val dailyPlan: StateFlow<List<MealPlanEntity>> = _selectedDate
        .flatMapLatest { date ->
            repository.getPlanForDate(dateFormatter.format(date))
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- СОБЫТИЯ ---
    fun selectDate(date: Date) {
        _selectedDate.value = date
    }

    fun removeMeal(id: Int) {
        viewModelScope.launch { repository.deleteMeal(id) }
    }

    fun addMeal(mealType: MealType, recipeId: String) {
        viewModelScope.launch {
            repository.addMeal(dateFormatter.format(_selectedDate.value), mealType, recipeId)
        }
    }

    class Factory(
        private val repo: MealPlanRepository,
        private val waterRepository: WaterRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MealPlannerViewModel(
                repository = repo,
                waterRepository = waterRepository
            ) as T
        }
    }
}