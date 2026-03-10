package ru.plumsoftware.avocado.ui.screen.main.receipt.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.receipt.Receipt
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun FeaturedReceiptCard(
    receipt: Receipt,
    onClick: () -> Unit,
    matchingGoal: UserGoal?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // Высокий баннер
            .clip(RoundedCornerShape(Dimen.large))
            .iosClickable { onClick() }
    ) {
        // 1. Картинка на весь фон
        Image(
            painter = painterResource(receipt.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Градиент снизу (чтобы текст читался)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )

        // 3. Текст поверх картинки
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(Dimen.large)
        ) {
            // УМНЫЙ БЕЙДЖ
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimen.mediumHalf))
                    .background(Color.White.copy(alpha = 0.95f))
                    .padding(horizontal = 10.dp, vertical = Dimen.extraSmall)
            ) {
                if (matchingGoal != null) {
                    // Если совпало с целью -> показываем магию
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = null,
                            tint = IOSGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ИДЕАЛЬНО ДЛЯ: ${stringResource(matchingGoal.titleRes).uppercase()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = IOSGreen
                            )
                        )
                    }
                } else {
                    // Если нет целей или не совпало -> Стандарт
                    Text(
                        text = stringResource(R.string.recipes_recommended_badge),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimen.mediumHalf))

            // Время и Калории (белый текст)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${receipt.timeMinutes} мин",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${receipt.calories} ккал",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}