package ru.plumsoftware.avocado.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.iosClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Анимация прозрачности: при нажатии 0.5f, иначе 1f
    val opacity by animateFloatAsState(
        targetValue = if (isPressed) 0.5f else 1f,
        label = "opacity"
    )

    // Анимация масштаба (опционально, как в App Store): чуть сжимаем
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        label = "scale"
    )

    this
        .graphicsLayer {
            this.alpha = opacity
            this.scaleX = scale
            this.scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null, // УБИРАЕМ RIPPLE
            enabled = enabled,
            onClick = onClick
        )
}