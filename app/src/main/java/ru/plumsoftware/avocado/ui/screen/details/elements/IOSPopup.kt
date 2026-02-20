package ru.plumsoftware.avocado.ui.screen.details.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.unit.IntOffset

@Composable
fun IOSPopup(
    text: String,
    isVisible: Boolean,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        // Popup привязывается к родительскому Box (твоему Чипсу)
        Popup(
            // 1. ТЕПЕРЬ СНИЗУ ПО ЦЕНТРУ
            alignment = Alignment.BottomCenter,
            onDismissRequest = onDismiss,
            // 2. СДВИГАЕМ ВНИЗ (y = 20, чтобы был отступ от чипса)
            offset = IntOffset(0, 70)
        ) {
            // Анимация появления
            androidx.compose.animation.AnimatedVisibility(
                visible = true,
                enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(expandFrom = Alignment.Top),
                exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 200.dp) // Ограничиваем ширину, чтобы не было на весь экран
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(
                            // Темный фон (iOS style)
                            color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.inverseOnSurface, // Белый текст
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}