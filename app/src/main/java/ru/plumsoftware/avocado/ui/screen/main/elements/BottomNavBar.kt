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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
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
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.main.MainScreenStates
import ru.plumsoftware.avocado.ui.screen.main.elements.bottom_bar.BottomBarItem
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun BottomNavBar(
    onItemSelected: (MainScreenStates) -> Unit,
    onScannerClick: () -> Unit,
    initialSelection: Int = 0
) {
    var selected by remember { mutableIntStateOf(initialSelection) }
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
            title = R.string.scanner_title, // Твоя строка
            iconVector = Icons.Rounded.CameraAlt
        ),
        BottomBarItem(
            title = R.string.racion,
            iconRes = R.drawable.racion
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
            3 -> MainScreenStates.MealPlanner
            4 -> MainScreenStates.Settings
            else -> MainScreenStates.Empty
        }
        if (item != MainScreenStates.Empty) {
            onItemSelected(item)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.medium)
            .padding(bottom = Dimen.medium)
    ) {
        // --- 1. ФОН ПАНЕЛИ ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .clip(shape = MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface)
                //.shadow(elevation = Dimen.extraSmall, shape = MaterialTheme.shapes.large)
                //.border(width = 1.dp, shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.primary)
        )

        // --- 2. ИКОНКИ ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = Dimen.mediumHalf),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            list.forEachIndexed { index, item ->
                val isSelected = selected == index

                if (index == 2) {
                    Box(
                        modifier = Modifier
                            .offset(y = (-16).dp)
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(IOSGreen)
                            .iosClickable { onScannerClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.iconVector != null) {
                            Icon(
                                imageVector = item.iconVector,
                                contentDescription = stringResource(item.title),
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
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
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(item.iconRes),
                                contentDescription = stringResource(item.title),
                                colorFilter = if (isSelected) null else ColorFilter.tint(Color.Gray)
                            )
                        } else if (item.iconVector != null) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = item.iconVector,
                                contentDescription = stringResource(item.title),
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }

                        Text(
                            text = stringResource(item.title),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                }
            }
        }
    }
}