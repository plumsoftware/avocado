package ru.plumsoftware.avocado.ui.screen.main.list.elements

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun WaterTrackerCard(
    currentWaterMl: Int,
    goalWaterMl: Int = 2000,
    onAddWater: (Int) -> Unit
) {
    // Цвета воды (iOS Blue) - яркий на белом, чуть светлее на темном
    val waterColor = if (isSystemInDarkTheme()) Color(0xFF0A84FF) else Color(0xFF007AFF)
    val waterBgColor = waterColor.copy(alpha = 0.15f)

    // Анимация прогресс-бара
    val progress by animateFloatAsState(
        targetValue = (currentWaterMl.toFloat() / goalWaterMl.toFloat()).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "water_progress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimen.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ЛЕВАЯ ЧАСТЬ: Инфо и Прогресс
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = Dimen.large)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(waterBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.water_tracker_icon),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(Dimen.mediumHalf))

                    Text(
                        text = stringResource(R.string.water_tracker_title),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))

                // Текст: 750 / 2000 мл
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = currentWaterMl.toString(), // Числа конвертируются напрямую
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = waterColor
                        )
                    )
                    Text(
                        text = stringResource(R.string.water_tracker_goal_format, goalWaterMl),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 2.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.mediumHalf))

                // Прогресс бар
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.mediumHalf)
                        .clip(RoundedCornerShape(50)),
                    color = waterColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round
                )
            }

            // ПРАВАЯ ЧАСТЬ: Кнопка "+"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(waterColor)
                        .iosClickable { onAddWater(250) }, // +1 стакан
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.cd_add_water), // Из ресурсов
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.small))

                Text(
                    text = stringResource(R.string.water_add_btn),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}