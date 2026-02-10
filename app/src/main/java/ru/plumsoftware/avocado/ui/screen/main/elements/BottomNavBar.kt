package ru.plumsoftware.avocado.ui.screen.main.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.screen.main.MainScreenStates
import ru.plumsoftware.avocado.ui.screen.main.elements.bottom_bar.BottomBarItem
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun BottomNavBar(
    onItemSelected: (MainScreenStates) -> Unit
) {
    var selected by remember { mutableIntStateOf(0) }
    val list = listOf(
        BottomBarItem(
            title = R.string.main,
            iconRes = R.drawable.main_nav_bar_icon,
        ),
        BottomBarItem(
            title = R.string.rec,
            iconRes = R.drawable.chef,
        ),
        BottomBarItem(
            title = R.string.fav,
            iconRes = R.drawable.fav,
        ),
        BottomBarItem(
            title = R.string.settings,
            iconRes = R.drawable.settings,
        )
    )

    LaunchedEffect(key1 = selected) {
        val item = when (selected) {
            0 -> MainScreenStates.List
            1 -> MainScreenStates.Rec
            2 -> MainScreenStates.Fav
            else -> MainScreenStates.Settings
        }
        onItemSelected(item)
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = Dimen.medium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.large)
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    horizontal = Dimen.large,
                    vertical = Dimen.mediumHalf
                )
        ) {
            list.forEachIndexed { index, item ->
                val isSelected = selected == index

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            enabled = true,
                            onClick = { selected = index }
                        ),
                    verticalArrangement = Arrangement.spacedBy(
                        space = Dimen.extraSmall,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (item.iconRes != null) {
                        Image(
                            modifier = Modifier
                                .size(28.dp),
                            painter = painterResource(item.iconRes),
                            contentDescription = stringResource(item.title),
                            colorFilter = if (isSelected) {
                                null
                            } else {
                                ColorFilter.tint(Color.Gray)
                            }
                        )
                    } else if (item.iconVector != null) {
                        Icon(
                            modifier = Modifier
                                .size(28.dp),
                            imageVector = item.iconVector,
                            contentDescription = stringResource(item.title),
                            tint = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Gray
                            }
                        )
                    }

                    Text(
                        text = stringResource(item.title),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Gray
                        }
                    )
                }
            }
        }
    }
}