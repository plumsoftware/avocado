package ru.plumsoftware.avocado.ui.screen.main.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun IOSAlertDialog(
    title: String,
    message: String,
    buttonText: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        // Контейнер диалога (Ширина в iOS обычно 270dp)
        Column(
            modifier = Modifier
                .width(270.dp)
                .clip(MaterialTheme.shapes.medium)
                // В светлой теме белый, в темной темно-серый (оба с легкой прозрачностью для "стекла")
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
        ) {
            // Текстовый блок
            Column(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = Dimen.medium,
                        start = Dimen.medium,
                        end = Dimen.medium
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimen.extraSmall))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // Тонкий разделитель iOS
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Кнопка (На всю ширину внизу)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .iosClickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = Color(0xFF007AFF) // Синий цвет системных кнопок iOS
                )
            }
        }
    }
}