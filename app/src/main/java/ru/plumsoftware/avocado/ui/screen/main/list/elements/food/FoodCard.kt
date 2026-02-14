package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.ui.screen.main.list.getLightenedColor
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun FoodCard(item: Food, onGetColor: (Int, Context) -> Int, modifier: Modifier) {

    val context = LocalContext.current
    val backgroundColor = remember(item.imageRes) {
        getLightenedColor(onGetColor(item.imageRes, context), factor = 0.2f)
    }
    val lighterColor03 = remember(item.imageRes) {
        getLightenedColor(onGetColor(item.imageRes, context), factor = 0.35f)
    }
    val lighterColor07 = remember(item.imageRes) {
        getLightenedColor(onGetColor(item.imageRes, context), factor = 0.7f)
    }
    val imageContainerHeight = 110.dp
    val containerHeight = imageContainerHeight.value.plus(40).dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(containerHeight)
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                enabled = true,
                interactionSource = remember { MutableInteractionSource() },
                role = Role.Button,
                indication = ripple(
                    bounded = false,
                    radius = 260.dp
                ),
                onClick = { }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Контейнер с изображением и градиентным фоном
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(lighterColor07),
                                Color(lighterColor03),
                                Color(backgroundColor)
                            ),
                            radius = 220f
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Image(
                    modifier = Modifier
                        .size(imageContainerHeight)
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    painter = painterResource(item.imageRes),
                    contentDescription = stringResource(item.titleRes),
                    contentScale = ContentScale.FillBounds
                )
            }

            // Текст снизу
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.mediumHalf, vertical = Dimen.mediumHalf),
                text = stringResource(item.titleRes),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}