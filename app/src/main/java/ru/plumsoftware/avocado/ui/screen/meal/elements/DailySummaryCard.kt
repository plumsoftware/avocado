package ru.plumsoftware.avocado.ui.screen.meal.elements

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.screen.meal.elements.DailyTotals
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun DailySummaryCard(totals: DailyTotals) {
    // 🔥 Анимация бегущих цифр при добавлении/удалении блюд
    val animKcal by animateIntAsState(targetValue = totals.kals, animationSpec = tween(500), label = "kcal")
    val animP by animateIntAsState(targetValue = totals.proteins.toInt(), animationSpec = tween(500), label = "p")
    val animF by animateIntAsState(targetValue = totals.fats.toInt(), animationSpec = tween(500), label = "f")
    val animC by animateIntAsState(targetValue = totals.carbs.toInt(), animationSpec = tween(500), label = "c")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f), RoundedCornerShape(Dimen.large))
            .padding(Dimen.large)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // Верхняя часть: Итого за день и Калории
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.planner_daily_summary), // "Итого за день"
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$animKcal",
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = " " + stringResource(R.string.meta_kcal).lowercase(), // " ккал"
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                // Если пусто - показываем подсказку (иконку огонька)
                if (totals.kals == 0) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier
                            .size(32.dp)
                            .padding(bottom = 8.dp)
                    )
                }
            }

            // Нижняя часть: БЖУ, показываем только если есть калории
            if (totals.kals > 0) {
                Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Цвета берем из системных (Зеленый, Оранжевый, Синий)
                    SummaryMacroItem(value = animP, label = stringResource(R.string.macro_proteins), color = Color(0xFF34C759))
                    SummaryMacroItem(value = animF, label = stringResource(R.string.macro_fats), color = Color(0xFFFF9500))
                    SummaryMacroItem(value = animC, label = stringResource(R.string.macro_carbs), color = Color(0xFF007AFF))
                }
            } else {
                // Сообщение, если день пустой
                Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))
                Text(
                    text = stringResource(R.string.planner_empty_summary), // "Добавьте блюда, чтобы увидеть расчет"
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun SummaryMacroItem(value: Int, label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = "${value}г",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}