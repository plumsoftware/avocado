package ru.plumsoftware.avocado.data.notification

import android.content.Context
import ru.plumsoftware.avocado.R
import java.util.Calendar
import kotlin.random.Random

data class NotificationData(val title: String, val body: String, val targetRoute: String? = null)

object NotificationArgs {
    const val DESTINATION_ROUTE = "DESTINATION_ROUTE"
}

object NotificationContentHelper {

    // Жесткая привязка к массивам из strings.xml
    // Порядок должен ТОЧНО совпадать с тем, как они записаны в XML!

    private val morningRoutes = listOf(
        "receipt/avocado_toast",   // 0: Как насчет тоста с авокадо...
        "receipt/fruit_salad",     // 1: Фруктовый салат зарядит энергией...
        "receipt/spinach_omelet"   // 2: Пышный омлет со шпинатом...
    )

    private val dayRoutes = listOf(
        "food/broccoli",           // 0: Брокколи защитник иммунитета...
        "food/kiwi",               // 1: В киви больше витамина С...
        "food/almond"              // 2: Горсть миндаля спасет... (мы добавили миндаль ранее)
    )

    private val eveningRoutes = listOf(
        "receipt/baked_salmon",    // 0: Запеченный лосось...
        "receipt/ratatouille",     // 1: Домашний рататуй...
        "food/spinach"             // 2: Продукты с магнием (шпинат)...
    )

    fun getDailyContent(context: Context): NotificationData {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val resources = context.resources

        // 1. Выбираем, какой блок данных брать
        val (titlesRes, bodiesRes, routes) = when {
            currentHour in 6..11 -> Triple(
                R.array.notif_morning_titles,
                R.array.notif_morning_bodies,
                morningRoutes
            )

            currentHour in 12..17 -> Triple(
                R.array.notif_day_titles,
                R.array.notif_day_bodies,
                dayRoutes
            )

            else -> Triple(
                R.array.notif_evening_titles,
                R.array.notif_evening_bodies,
                eveningRoutes
            )
        }

        val titles = resources.getStringArray(titlesRes)
        val bodies = resources.getStringArray(bodiesRes)

        // 2. Выбираем случайный индекс (например, от 0 до 2)
        val randomIndex = Random.nextInt(titles.size)

        // 3. Собираем всё вместе
        return NotificationData(
            title = titles[randomIndex],
            body = bodies[randomIndex],
            targetRoute = routes[randomIndex] // <--- Наш Диплинк
        )
    }
}