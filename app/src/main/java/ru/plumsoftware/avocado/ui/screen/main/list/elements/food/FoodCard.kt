package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun FoodCard(item: Food, onGetColor: (Int, Context) -> Int, modifier: Modifier) {

    val context = LocalContext.current
    val backgroundColor = remember(item.imageRes) {
        onGetColor(item.imageRes, context)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                onClick = {

                }
            )
            .then(modifier)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = Dimen.mediumHalf),
            verticalArrangement = Arrangement.spacedBy(
                space = Dimen.mediumHalf,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(all = Dimen.medium)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        color = Color(backgroundColor),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(item.imageRes),
                    contentDescription = stringResource(item.titleRes),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                text = stringResource(item.titleRes),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}