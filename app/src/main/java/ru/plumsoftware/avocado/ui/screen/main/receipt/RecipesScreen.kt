package ru.plumsoftware.avocado.ui.screen.main.receipt

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.data.base.model.receipt.RecipeCategory
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipesScreen(
    navController: NavHostController,
    userPreferencesRepository: UserPreferencesRepository
) {
    val viewModel: RecipesViewModel =
        viewModel(factory = RecipesViewModel.Factory(userPrefsRepo = userPreferencesRepository))

    val featuredReceipts by viewModel.featuredReceipts.collectAsState()
    val recipeList by viewModel.recipeList.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val userGoals by viewModel.userGoals.collectAsState()

    val pagerState = rememberPagerState(pageCount = { featuredReceipts.size })

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimen.medium),
            verticalArrangement = Arrangement.spacedBy(Dimen.large),
            contentPadding = PaddingValues(bottom = Dimen.bottomNavPadding)
        ) {

            item {
                Spacer(modifier = Modifier.height(Dimen.medium))
            }

            // 1. ЗАГОЛОВОК ЭКРАНА
            item {
                Spacer(modifier = Modifier.height(Dimen.spacerVertical))
                Text(
                    text = stringResource(R.string.recipes_title),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = stringResource(R.string.recipes_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // 2. БАННЕР (VIEW PAGER)
            if (featuredReceipts.isNotEmpty()) {
                item {
                    Column {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            pageSpacing = Dimen.medium
                        ) { page ->
                            val receipt = featuredReceipts[page]
                            // Ищем первое совпадение с целью юзера
                            val matchingGoal =
                                receipt.suitableGoals.firstOrNull { userGoals.contains(it) }

                            FeaturedReceiptCard(
                                receipt = receipt,
                                matchingGoal = matchingGoal,
                                onClick = {
                                    navController.navigate(
                                        AppDestination.ReceiptDetailRoute(
                                            receipt.id
                                        )
                                    )
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(Dimen.mediumHalf))

                        // Индикатор (Точки)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(featuredReceipts.size) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) IOSGreen else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(Dimen.extraSmall)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(Dimen.small)
                                )
                            }
                        }
                    }
                }
            }

            // 3. ФИЛЬТРЫ (Категории)
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        val bgColor =
                            if (isSelected) IOSGreen else MaterialTheme.colorScheme.surface
                        val contentColor =
                            if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        val borderColor =
                            if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outlineVariant

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(bgColor)
                                .border(1.dp, borderColor, RoundedCornerShape(50))
                                .clickable { viewModel.onCategorySelect(category) }
                                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf)
                        ) {
                            Text(
                                text = stringResource(category.titleRes),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = contentColor
                                )
                            )
                        }
                    }
                }
            }

            // 4. ЗАГОЛОВОК СПИСКА
            item {
                Text(
                    text = if (selectedCategory == RecipeCategory.ALL)
                        stringResource(R.string.recipes_header_all)
                    else stringResource(selectedCategory.titleRes),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // 5. СПИСОК РЕЦЕПТОВ
            if (recipeList.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.recipes_empty_category),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = Dimen.extraLarge)
                    )
                }
            } else {
                items(recipeList) { receipt ->
                    val matchingGoal = receipt.suitableGoals.firstOrNull { userGoals.contains(it) }

                    ReceiptListItem(
                        receipt = receipt,
                        matchingGoal = matchingGoal,
                        onClick = { navController.navigate(AppDestination.ReceiptDetailRoute(receipt.id)) }
                    )
                }
            }
        }
    }
}

// ==========================================
// КОМПОНЕНТЫ UI
// ==========================================

@Composable
fun FeaturedReceiptCard(
    receipt: TypicalReceipt,
    onClick: () -> Unit,
    matchingGoal: UserGoal?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.bannerHeight)
            .clip(RoundedCornerShape(Dimen.large))
            .iosClickable { onClick() }
    ) {
        Image(
            painter = painterResource(receipt.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )

        // 3. Текст поверх картинки
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimen.large)
        ) {
            // УМНЫЙ БЕЙДЖ
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimen.mediumHalf))
                    .background(Color.White.copy(alpha = 0.95f))
                    .padding(horizontal = Dimen.mediumAboveHalf, vertical = Dimen.extraSmall)
            ) {
                if (matchingGoal != null) {
                    // Если совпало с целью -> показываем магию
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = null,
                            tint = IOSGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(Dimen.extraSmall))
                        Text(
                            text = stringResource(matchingGoal.titleRes).uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = IOSGreen
                            )
                        )
                    }
                } else {
                    // Если нет целей или не совпало -> Стандарт
                    Text(
                        text = stringResource(R.string.recipes_recommended_badge),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimen.mediumHalf))

            Text(
                text = stringResource(receipt.titleRes),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimen.extraSmall))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(Dimen.iconSizeTiny)
                )
                Spacer(modifier = Modifier.width(Dimen.extraSmall))
                Text(
                    text = stringResource(R.string.format_minutes, receipt.timeMinutes),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )

                Spacer(modifier = Modifier.width(Dimen.mediumAboveHalf))

                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(Dimen.iconSizeTiny)
                )
                Spacer(modifier = Modifier.width(Dimen.extraSmall))
                Text(
                    text = stringResource(R.string.format_kcal, receipt.calories),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}

@Composable
fun ReceiptListItem(
    receipt: TypicalReceipt,
    onClick: () -> Unit,
    matchingGoal: UserGoal?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.medium))
            .background(MaterialTheme.colorScheme.surface)
            .iosClickable { onClick() }
            .padding(bottom = Dimen.medium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.receiptItemImageHeight)
                .clip(RoundedCornerShape(Dimen.medium))
        ) {
            Image(
                painter = painterResource(receipt.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(Dimen.mediumHalf))

        // Текстовый блок
        Column(modifier = Modifier.padding(horizontal = Dimen.extraSmall)) {

            // --- УМНАЯ СНОСКА ДЛЯ СПИСКА ---
            if (matchingGoal != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = Dimen.mediumHalf, bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AutoAwesome,
                        contentDescription = null,
                        tint = IOSGreen,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Поможет ${stringResource(matchingGoal.titleRes).lowercase()}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = IOSGreen
                        )
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(Dimen.mediumHalf))
            }

            Text(
                text = stringResource(receipt.titleRes),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimen.extraSmall))

            Text(
                text = stringResource(receipt.descRes),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimen.mediumHalf))

            Row(horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)) {
                ReceiptMetaChip(
                    icon = Icons.Default.Schedule,
                    text = stringResource(R.string.format_minutes, receipt.timeMinutes)
                )
                ReceiptMetaChip(
                    icon = Icons.Default.Bolt,
                    text = "${receipt.difficulty}/3"
                )
            }
        }
    }
}

@Composable
fun ReceiptMetaChip(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp)) // Можно тоже в Dimen
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(Dimen.iconSizeMicro),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(Dimen.extraSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}