package ru.plumsoftware.avocado.ui.screen.main.list.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun IOSUpdateDialog(
    onUpdateClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onCancelClick,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false // Заставляем юзера нажать кнопку
        )
    ) {
        Column(
            modifier = Modifier
                .width(280.dp) // Классическая ширина iOS-диалога
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- МАСКОТ ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9)) // Очень светлый зеленый фон под маскота
                    .padding(vertical = Dimen.large),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.avo_update),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // --- ТЕКСТ ---
            Column(
                modifier = Modifier.padding(horizontal = Dimen.medium, vertical = Dimen.mediumAboveHalf),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.update_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimen.extraSmall))

                Text(
                    text = stringResource(R.string.update_message),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // --- КНОПКИ (Горизонтально или вертикально) ---
            // В iOS если кнопок две, они часто идут в ряд.
            Row(modifier = Modifier.fillMaxWidth().height(46.dp)) {
                // Кнопка ОТМЕНА (Обычный текст)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .iosClickable { onCancelClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.update_btn_cancel),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                        color = Color(0xFF007AFF) // iOS Blue
                    )
                }

                VerticalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )

                // Кнопка ОБНОВИТЬ (Жирный текст - Primary Action)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .iosClickable { onUpdateClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.update_btn_update),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        ),
                        color = Color(0xFF007AFF) // iOS Blue
                    )
                }
            }
        }
    }
}