package ru.plumsoftware.avocado.ui.widget

import android.annotation.SuppressLint
import androidx.glance.appwidget.provideContent
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.water.WaterRepository
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.AppWidgetId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import ru.plumsoftware.avocado.MainActivity
import ru.plumsoftware.avocado.R
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition

// Ключ для передачи диплинка в MainActivity
val DestinationKey = ActionParameters.Key<String>("DESTINATION_ROUTE")

// Вспомогательная функция для клика (Открывает приложение на нужном экране)
fun actionOpenApp(route: String) = actionStartActivity<MainActivity>(
    actionParametersOf(DestinationKey to route)
)

// =====================================================================
// 📸 ВИДЖЕТ 1: AI СКАНЕР (1x1)
// =====================================================================
class ScannerWidget : GlanceAppWidget() {
    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ImageProvider(R.drawable.apple)) // Используй зеленую заливку
                    .cornerRadius(16.dp)
                    .clickable(actionOpenApp("scanner")),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        provider = ImageProvider(R.drawable.camera_icon),
                        contentDescription = "Scanner",
                        modifier = GlanceModifier.size(32.dp)
                    )
                    Spacer(modifier = GlanceModifier.height(8.dp))
                    Text(
                        text = "AI Сканер",
                        style = TextStyle(color = ColorProvider(R.color.white), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    )
                }
            }
        }
    }
}

// =====================================================================
// 🎛 ВИДЖЕТ 2: КВИК-ЭКШЕНЫ / CONTROL CENTER (2x2)
// =====================================================================
class QuickActionsWidget : GlanceAppWidget() {
    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(R.color.white)) // Или iOS System Gray 6
                    .cornerRadius(16.dp)
                    .padding(12.dp)
            ) {
                Row(modifier = GlanceModifier.defaultWeight().fillMaxWidth()) {
                    WidgetButton("Рацион", R.drawable.racion, "planner", GlanceModifier.defaultWeight())
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    WidgetButton("Покупки", R.drawable.shopping_cart, "shopping", GlanceModifier.defaultWeight())
                }
                Spacer(modifier = GlanceModifier.height(8.dp))
                Row(modifier = GlanceModifier.defaultWeight().fillMaxWidth()) {
                    WidgetButton("Сканер", R.drawable.camera_icon, "scanner", GlanceModifier.defaultWeight())
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    WidgetButton("Избранное", R.drawable.fav, "favorite", GlanceModifier.defaultWeight())
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun WidgetButton(title: String, iconRes: Int, route: String, modifier: GlanceModifier) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .background(ColorProvider(R.color.gray_light))
                .cornerRadius(12.dp)
                .clickable(actionOpenApp(route)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(provider = ImageProvider(iconRes), contentDescription = null, modifier = GlanceModifier.size(24.dp))
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(text = title, style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium))
            }
        }
    }
}

// =====================================================================
// 💧 ВИДЖЕТ 3: ТРЕКЕР ВОДЫ (Интерактивный!)
// =====================================================================
class WaterWidget : GlanceAppWidget() {
    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Запрашиваем данные из БД при отрисовке виджета
        val db = AvocadoDatabase.getDatabase(context)
        val waterRepo = WaterRepository(db.waterIntakeDao())
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentWater = waterRepo.getWaterForDate(todayString).first() ?: 0

        provideContent {
            Row(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(R.color.white))
                    .cornerRadius(16.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text("Водный баланс", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp))
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Text("$currentWater / 2000 мл", style = TextStyle(color = ColorProvider(R.color.ios_blue), fontWeight = FontWeight.Bold, fontSize = 18.sp))
                }

                Box(
                    modifier = GlanceModifier
                        .size(48.dp)
                        .background(ColorProvider(R.color.ios_blue))
                        .cornerRadius(24.dp)
                        .clickable(actionRunCallback<AddWaterCallback>()),
                    contentAlignment = Alignment.Center
                ) {
                    Image(provider = ImageProvider(R.drawable.ic_add_white), contentDescription = "+", modifier = GlanceModifier.size(24.dp))
                }
            }
        }
    }
}

// Коллбек для Интерактивного виджета воды
class AddWaterCallback : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val db = AvocadoDatabase.getDatabase(context)
        val waterRepo = WaterRepository(db.waterIntakeDao())
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val currentAmount = waterRepo.getWaterForDate(todayString).first() ?: 0
        waterRepo.addWater(todayString, currentAmount, 250) // Добавляем 1 стакан

        // Обновляем виджет, чтобы цифра изменилась на экране!
        WaterWidget().update(context, glanceId)
    }
}

// =====================================================================
// 🥗 ВИДЖЕТ 4: РЕЦЕПТ ДНЯ
// =====================================================================
class RecipeWidget : GlanceAppWidget() {

    // 🔥 ВАЖНО: Указываем, что виджет использует Preferences для хранения состояния
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            // 🔥 Читаем состояние текущего виджета
            val prefs = currentState<Preferences>()
            val recipeId = prefs[stringPreferencesKey("widget_recipe_id")] ?: "avocado_toast"

            val recipe = ru.plumsoftware.avocado.data.base.model.receipt.allReceipts.find { it.id == recipeId }

            if (recipe != null) {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .cornerRadius(16.dp)
                        .clickable(actionOpenApp("receipt/${recipe.id}")),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Image(
                        provider = ImageProvider(recipe.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = GlanceModifier.fillMaxSize()
                    )

                    Box(modifier = GlanceModifier.fillMaxSize().background(ColorProvider(R.color.black_alpha_40))) {}

                    Column(modifier = GlanceModifier.padding(12.dp)) {
                        Text(
                            text = "Рецепт дня",
                            style = TextStyle(color = ColorProvider(R.color.white), fontSize = 10.sp)
                        )
                        Text(
                            text = context.getString(recipe.titleRes),
                            style = TextStyle(color = ColorProvider(R.color.white), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        )
                    }
                }
            } else {
                Box(modifier = GlanceModifier.fillMaxSize().background(ColorProvider(R.color.gray_light))) {
                    Text("Рецепт не найден")
                }
            }
        }
    }
}