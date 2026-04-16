package ru.plumsoftware.avocado.ui.widget

import android.annotation.SuppressLint
import androidx.glance.appwidget.provideContent
import ru.plumsoftware.avocado.data.database.AvocadoDatabase
import ru.plumsoftware.avocado.data.water.WaterRepository
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
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

// Ключ для диплинков
val DestinationKey = ActionParameters.Key<String>("DESTINATION_ROUTE")
fun actionOpenApp(route: String) = actionStartActivity<MainActivity>(actionParametersOf(DestinationKey to route))

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
                    .background(ColorProvider(R.color.white))
                    .cornerRadius(R.dimen.widget_radius)
                    .clickable(actionOpenApp("scanner")),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        provider = ImageProvider(R.drawable.camera_icon), // Иконка
                        contentDescription = null,
                        modifier = GlanceModifier.size(48.dp)
                    )
                    Spacer(modifier = GlanceModifier.height(R.dimen.widget_padding_small))
                    Text(
                        text = context.getString(R.string.widget_scanner_title),
                        style = TextStyle(
                            color = ColorProvider(R.color.black),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
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
                    .background(ColorProvider(R.color.widget_background))
                    .cornerRadius(R.dimen.widget_radius)
                    .padding(R.dimen.widget_padding_medium)
            ) {
                Row(modifier = GlanceModifier.defaultWeight().fillMaxWidth()) {
                    WidgetButton(context.getString(R.string.widget_btn_planner), R.drawable.racion, "planner", GlanceModifier.defaultWeight())
                    Spacer(modifier = GlanceModifier.width(R.dimen.widget_padding_small))
                    WidgetButton(context.getString(R.string.widget_btn_shopping), R.drawable.shopping_cart, "shopping", GlanceModifier.defaultWeight())
                }
                Spacer(modifier = GlanceModifier.height(R.dimen.widget_padding_small))
                Row(modifier = GlanceModifier.defaultWeight().fillMaxWidth()) {
                    WidgetButton(context.getString(R.string.widget_btn_scanner), R.drawable.camera_icon, "scanner", GlanceModifier.defaultWeight())
                    Spacer(modifier = GlanceModifier.width(R.dimen.widget_padding_small))
                    WidgetButton(context.getString(R.string.widget_btn_favorite), R.drawable.fav_2_apple, "favorite", GlanceModifier.defaultWeight())
                }
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
            .background(ColorProvider(R.color.widget_surface))
            .cornerRadius(12.dp)
            .clickable(actionOpenApp(route)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(provider = ImageProvider(iconRes), contentDescription = null, modifier = GlanceModifier.size(24.dp))
            Spacer(modifier = GlanceModifier.height(4.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorProvider(R.color.widget_text_primary)
                )
            )
        }
    }
}

// =====================================================================
// 💧 ВИДЖЕТ 3: ТРЕКЕР ВОДЫ (Интерактивный!)
// =====================================================================
class WaterWidget : GlanceAppWidget() {
    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val currentWater by produceState(initialValue = 0) {
                val db = AvocadoDatabase.getDatabase(context)
                val waterRepo = WaterRepository(db.waterIntakeDao())
                val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                waterRepo.getWaterForDate(todayString).collect { value ->
                    this.value = value
                }
            }

            Row(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ColorProvider(R.color.widget_background))
                    .cornerRadius(R.dimen.widget_radius)
                    .padding(R.dimen.widget_padding_medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text(
                        text = context.getString(R.string.widget_water_title),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = ColorProvider(R.color.widget_text_primary)
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Text(
                        text = context.getString(R.string.widget_water_format, currentWater, 2000),
                        style = TextStyle(
                            color = ColorProvider(R.color.widget_blue),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }

                Box(
                    modifier = GlanceModifier
                        .size(48.dp)
                        .background(ColorProvider(R.color.widget_blue))
                        .cornerRadius(24.dp)
                        .clickable(actionRunCallback<AddWaterCallback>()),
                    contentAlignment = Alignment.Center
                ) {
                    // Используй свою белую иконку плюса
                    Image(
                        provider = ImageProvider(R.drawable.ic_add_white),
                        contentDescription = "+",
                        modifier = GlanceModifier.size(24.dp)
                    )
                }
            }
        }
    }
}

class AddWaterCallback : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val db = AvocadoDatabase.getDatabase(context)
        val waterRepo = WaterRepository(db.waterIntakeDao())
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val currentAmount = waterRepo.getWaterForDate(todayString).first() ?: 0
        waterRepo.addWater(todayString, currentAmount, 250)

        // Виджет обновится сам, благодаря produceState и Flow в provideContent!
    }
}

// =====================================================================
// 🥗 ВИДЖЕТ 4: РЕЦЕПТ ДНЯ
// =====================================================================
class RecipeWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val recipeId = prefs[stringPreferencesKey("widget_recipe_id")] ?: "avocado_toast"

            val recipe = ru.plumsoftware.avocado.data.base.model.receipt.allReceipts.find { it.id == recipeId }

            if (recipe != null) {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .cornerRadius(R.dimen.widget_radius)
                        .clickable(actionOpenApp("receipt/${recipe.id}")),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Image(
                        provider = ImageProvider(recipe.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = GlanceModifier.fillMaxSize()
                    )

                    // Градиент / Затемнение
                    Box(modifier = GlanceModifier.fillMaxSize().background(ColorProvider(R.color.widget_scrim))) {}

                    Column(modifier = GlanceModifier.padding(R.dimen.widget_padding_medium)) {
                        Text(
                            text = context.getString(R.string.widget_recipe_title),
                            style = TextStyle(color = ColorProvider(R.color.widget_white), fontSize = 10.sp)
                        )
                        Text(
                            text = context.getString(recipe.titleRes),
                            style = TextStyle(
                                color = ColorProvider(R.color.widget_white),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            } else {
                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .background(ColorProvider(R.color.widget_surface))
                        .cornerRadius(R.dimen.widget_radius),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = context.getString(R.string.widget_recipe_empty),
                        style = TextStyle(color = ColorProvider(R.color.widget_text_secondary))
                    )
                }
            }
        }
    }
}