@file:Suppress("CAST_NEVER_SUCCEEDS")

package ru.plumsoftware.avocado.ui.screen.main.list.elements.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun FilterItem(item: Filter, onFilterClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = if (item.isSelected) 1.dp else 0.dp,
                color = if (item.isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .iosClickable(
                enabled = true,
                onClick = {
                    onFilterClick()
                }
            ),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = Dimen.extraSmall, horizontal = Dimen.mediumHalf),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = Dimen.extraSmall,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(item.icon),
                contentDescription = stringResource(item.title)
            )

            Text(
                text = stringResource(item.title),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}