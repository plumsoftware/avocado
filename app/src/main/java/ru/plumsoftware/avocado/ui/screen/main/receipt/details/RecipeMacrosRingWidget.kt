package ru.plumsoftware.avocado.ui.screen.main.receipt.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.avocado.ui.screen.details.StatsLegendItem
import ru.plumsoftware.avocado.ui.screen.main.meal.elements.DailyTotals
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R

@Composable
fun RecipeMacrosRingWidget(totals: DailyTotals) {
    // Масштабируем кольца, чтобы рецепт на 400-500 ккал заполнял их красиво
    val scaleFactor = 0.5f
    val goalKcal = 2000f * scaleFactor
    val goalProteins = 100f * scaleFactor
    val goalFats = 70f * scaleFactor
    val goalCarbs = 250f * scaleFactor

    val animKcal by animateIntAsState(targetValue = totals.kals, animationSpec = tween(1000), label = "kcal")
    val pKcal by animateFloatAsState(targetValue = (totals.kals / goalKcal).coerceIn(0f, 1f), animationSpec = tween(1000), label = "pKcal")
    val pProt by animateFloatAsState(targetValue = (totals.proteins / goalProteins).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pProt")
    val pFats by animateFloatAsState(targetValue = (totals.fats / goalFats).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pFats")
    val pCarbs by animateFloatAsState(targetValue = (totals.carbs / goalCarbs).toFloat().coerceIn(0f, 1f), animationSpec = tween(1000), label = "pCarbs")

    val colorKcal = Color(0xFFFF3B30) // Красный
    val colorProt = Color(0xFF34C759) // Зеленый
    val colorFats = Color(0xFFFF9500) // Оранжевый
    val colorCarbs = Color(0xFF007AFF) // Синий

    val trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            // Легкая серая подложка вместо жесткой рамки (iOS Style)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(Dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ЛЕВАЯ ЧАСТЬ: Цифры
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Пищевая ценность",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$animKcal",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = " ккал",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Легенда БЖУ
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatsLegendItem(stringResource(R.string.macro_proteins), totals.proteins.toInt(), colorProt)
                    StatsLegendItem(stringResource(R.string.macro_fats), totals.fats.toInt(), colorFats)
                    StatsLegendItem(stringResource(R.string.macro_carbs), totals.carbs.toInt(), colorCarbs)
                }
            }

            // ПРАВАЯ ЧАСТЬ: КОЛЬЦА
            Box(
                modifier = Modifier.size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(90.dp)) {
                    val strokeWidth = 14f
                    val spacing = 20f
                    val center = center

                    val radius1 = size.minDimension / 2 - strokeWidth / 2
                    val radius2 = radius1 - spacing
                    val radius3 = radius2 - spacing
                    val radius4 = radius3 - spacing

                    fun drawRing(radius: Float, progress: Float, activeColor: Color) {
                        drawCircle(color = trackColor, radius = radius, center = center, style = Stroke(width = strokeWidth))
                        if (progress > 0) {
                            drawArc(
                                color = activeColor,
                                startAngle = -90f,
                                sweepAngle = 360f * progress,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                                topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius),
                                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                            )
                        }
                    }

                    drawRing(radius1, pKcal, colorKcal)
                    drawRing(radius2, pProt, colorProt)
                    drawRing(radius3, pFats, colorFats)
                    drawRing(radius4, pCarbs, colorCarbs)
                }
            }
        }
    }
}