package ru.plumsoftware.avocado.data.user_preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.onboarding.UserRestriction
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.snippets.proto.AppThemeProto
import ru.plumsoftware.avocado.snippets.proto.UserPreferences

class UserPreferencesRepository(
    private val dataStore: DataStore<UserPreferences>
) {

    // Статус: Пройден ли онбординг? (По умолчанию в proto false)
    val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs.isOnboardingCompleted
    }

    // Тема приложения
    val appTheme: Flow<AppTheme> = dataStore.data.map { prefs ->
        when (prefs.appTheme) {
            AppThemeProto.LIGHT -> AppTheme.Light
            AppThemeProto.DARK -> AppTheme.Dark
            else -> AppTheme.System
        }
    }

    // Список целей пользователя (превращаем строки из БД обратно в Enum UserGoal)
    val userGoals: Flow<List<UserGoal>> = dataStore.data.map { prefs ->
        prefs.userGoalsList.mapNotNull { goalName ->
            try {
                UserGoal.valueOf(goalName) // Ищем Enum по имени "LOSE_WEIGHT"
            } catch (e: Exception) {
                null // Если такого енама уже нет, игнорируем
            }
        }
    }


    // Сохранить тему
    suspend fun setAppTheme(theme: AppTheme) {
        val protoTheme = when (theme) {
            AppTheme.Light -> AppThemeProto.LIGHT
            AppTheme.Dark -> AppThemeProto.DARK
            AppTheme.System -> AppThemeProto.SYSTEM
        }

        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setAppTheme(protoTheme)
                .build()
        }
    }

    /**
     * Самая главная функция. Вызывается, когда юзер нажал "Начать" в конце онбординга.
     * Она сохраняет всё сразу: цели, ограничения и ставит галочку "Готово".
     */
    suspend fun completeOnboarding(
        goals: List<UserGoal>,
        restrictions: List<UserRestriction>
    ) {
        dataStore.updateData { prefs ->
            prefs.toBuilder()
                // 1. Ставим флаг, что онбординг пройден
                .setIsOnboardingCompleted(true)

                // 2. Ставим флаг, что это уже не первый запуск (на всякий случай)
                .setFirstLaunch(false)

                // 3. Сохраняем Цели (превращаем Enum в String: LOSE_WEIGHT -> "LOSE_WEIGHT")
                .clearUserGoals() // Сначала очищаем старое, чтобы не дублировать
                .addAllUserGoals(goals.map { it.name })

                // 4. Сохраняем Ограничения
                .clearUserRestrictions()
                .addAllUserRestrictions(restrictions.map { it.name })

                .build()
        }
    }
}