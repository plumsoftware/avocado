package ru.plumsoftware.avocado.ui.screen.main.list.elements

import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun IOSTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearchFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    onFilterClick: () -> Unit,
    cartItemsCount: Int,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchBarColor = MaterialTheme.colorScheme.surfaceVariant
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- 1. СТРОКА ПОИСКА ---
        Box(
            modifier = Modifier
                .weight(1f) // Занимает всё свободное место
                .height(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(searchBarColor)
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .onFocusChanged { onFocusChange(it.isFocused) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_placeholder),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                )
                            }
                            innerTextField()
                        }

                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = stringResource(R.string.search_clear),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .size(16.dp)
                                    .iosClickable { onSearchQueryChange("") }
                            )
                        }
                    }
                }
            )
        }

        // --- 2. КНОПКА "ОТМЕНА" (Показывается при поиске) ---
        AnimatedVisibility(
            visible = isSearchFocused,
            enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
            exit = shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Dimen.mediumAboveHalf)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onSearchQueryChange("")
                        onFocusChange(false)
                        focusManager.clearFocus()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.search_cancel),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = !isSearchFocused,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Dimen.mediumHalf)
                    .size(36.dp) // Размер кликабельной зоны
                    .iosClickable { onCartClick() },
                contentAlignment = Alignment.Center
            ) {
                // Контейнер для иконки и бейджа
                Box {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = stringResource(R.string.cd_shopping_cart),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    // 🔥 IOS BADGE
                    if (cartItemsCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 6.dp, y = (-4).dp)
                                // 🔥 1. Вместо жесткого size используем defaultMinSize,
                                // чтобы бейдж мог растягиваться для "99+"
                                .defaultMinSize(minWidth = 14.dp, minHeight = 14.dp)
                                .background(Color(0xFFFF3B30), CircleShape)
                                // 🔥 2. Отступы по бокам, чтобы текст не прилипал к краям
                                .padding(horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (cartItemsCount > 99) "99+" else cartItemsCount.toString(),
                                color = Color.White,
                                // 7.sp слишком мелко даже для iOS, 9.sp читается идеально
                                fontSize = 7.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                // 🔥 3. МАГИЯ ЗДЕСЬ: отключаем системные отступы шрифта
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                                    lineHeight = 7.sp
                                ),
                                // 🔥 4. Оптическая корректировка (иногда Android все равно смещает текст шрифта Roboto на 1 пиксель вниз)
                                modifier = Modifier.offset(y = (-0.5).dp)
                            )
                        }
                    }
                }
            }
        }
    }
}