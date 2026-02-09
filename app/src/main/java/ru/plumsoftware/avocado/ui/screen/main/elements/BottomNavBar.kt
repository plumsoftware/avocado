package ru.plumsoftware.avocado.ui.screen.main.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.screen.main.elements.bottom_bar.BottomBarItem
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun BottomNavBar() {
    val list = listOf(
        BottomBarItem(
            title = R.string.main,
            iconRes = R.drawable.main_nav_bar_icon,
        )
    )

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
                    horizontal = Dimen.medium,
                    vertical = Dimen.mediumHalf
                )
        ) {
            list.forEach {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = Dimen.extraSmall,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (it.iconRes != null) {
                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(it.iconRes),
                            contentDescription = stringResource(it.title)
                        )
                        Text(
                            text = stringResource(it.title),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}