package ru.plumsoftware.avocado.ui.screen.main.meal.elements

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun StatsRingsWidget(totals: DailyTotals) {
    // Цели (Target Goals). В идеале их надо брать из DataStore,
    // рассчитывая на основе веса и роста (ИМТ). Пока берем стандарты для примера.
    val goalKcal = 2000f
    val goalProteins = 100f
    val goalFats = 70f
    val goalCarbs = 250f

    // Анимация прогресса (от 0 до целевого значения)
    val pKcal by animateFloatAsState(targetValue = (totals.kals / goalKcal).coerceIn(0f, 1f), animationSpec = tween(1000), label = "kcal")
    val pProt by animateFloatAsState(targetValue = (totals.proteins / goalProteins).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "prot")
    val pFats by animateFloatAsState(targetValue = (totals.fats / goalFats).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "fats")
    val pCarbs by animateFloatAsState(targetValue = (totals.carbs / goalCarbs).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "carbs")

    // Цвета колец
    val colorKcal = Color(0xFFFF3B30) // Красный iOS (Активность/Ккал)
    val colorProt = Color(0xFF34C759) // Зеленый iOS (Белки)
    val colorFats = Color(0xFFFF9500) // Оранжевый iOS (Жиры)
    val colorCarbs = Color(0xFF007AFF) // Синий iOS (Углеводы)

    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(Dimen.large))
            .padding(Dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ЛЕВАЯ ЧАСТЬ: ТЕКСТОВЫЕ ДАННЫЕ
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Дневная сводка",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${totals.kals}",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = " / ${goalKcal.toInt()} ккал",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Легенда БЖУ
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatsLegendItem("Белки", totals.proteins.toInt(), colorProt)
                    StatsLegendItem("Жиры", totals.fats.toInt(), colorFats)
                    StatsLegendItem("Углев", totals.carbs.toInt(), colorCarbs)
                }
            }

            // ПРАВАЯ ЧАСТЬ: КОЛЬЦА АКТИВНОСТИ (Canvas)
            Box(
                modifier = Modifier.size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(90.dp)) {
                    val strokeWidth = 14f // Толщина колец
                    val spacing = 20f     // Расстояние между кольцами
                    val center = center

                    // Радиусы для 4 колец (внешнее - Ккал, внутреннее - Углеводы)
                    val radius1 = size.minDimension / 2 - strokeWidth / 2
                    val radius2 = radius1 - spacing
                    val radius3 = radius2 - spacing
                    val radius4 = radius3 - spacing

                    // Вспомогательная функция для отрисовки кольца
                    fun drawRing(radius: Float, progress: Float, activeColor: Color) {
                        // Фоновое кольцо (трек)
                        drawCircle(color = trackColor, radius = radius, center = center, style = Stroke(width = strokeWidth))
                        // Активное кольцо (прогресс)
                        if (progress > 0) {
                            drawArc(
                                color = activeColor,
                                startAngle = -90f, // Начинаем сверху
                                sweepAngle = 360f * progress,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius),
                                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                            )
                        }
                    }

                    // Рисуем от внешнего к внутреннему
                    drawRing(radius1, pKcal, colorKcal)
                    drawRing(radius2, pProt, colorProt)
                    drawRing(radius3, pFats, colorFats)
                    drawRing(radius4, pCarbs, colorCarbs)
                }
            }
        }
    }
}

@Composable
fun StatsLegendItem(label: String, value: Int, color: Color) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "${value}г",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}