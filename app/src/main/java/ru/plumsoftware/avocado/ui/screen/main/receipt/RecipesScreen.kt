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
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipesScreen(
    navController: NavHostController
) {
    // Инициализация ViewModel
    val viewModel: RecipesViewModel = viewModel(factory = RecipesViewModel.Factory())

    // Сбор данных из StateFlow
    val featuredReceipts by viewModel.featuredReceipts.collectAsState()
    val recipeList by viewModel.recipeList.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

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
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(Dimen.medium))
            }

            // 1. ЗАГОЛОВОК ЭКРАНА
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Рецепты",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "Вкусное и полезное на каждый день",
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
                            FeaturedReceiptCard(
                                receipt = featuredReceipts[page],
                                onClick = {
                                    navController.navigate(AppDestination.ReceiptDetailRoute(featuredReceipts[page].id))
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
                                val color = if (pagerState.currentPage == iteration) IOSGreen else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 3. ФИЛЬТРЫ (Категории)
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        val bgColor = if (isSelected) IOSGreen else MaterialTheme.colorScheme.surface
                        val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        val borderColor = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outlineVariant

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(bgColor)
                                .border(1.dp, borderColor, RoundedCornerShape(50))
                                .clickable { viewModel.onCategorySelect(category) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category,
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
                    text = if (selectedCategory == "Все") "Все рецепты" else selectedCategory,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // 5. СПИСОК РЕЦЕПТОВ
            if (recipeList.isEmpty()) {
                item {
                    Text(
                        text = "В этой категории пока нет рецептов",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            } else {
                items(recipeList) { receipt ->
                    ReceiptListItem(
                        receipt = receipt,
                        onClick = {
                            navController.navigate(AppDestination.ReceiptDetailRoute(receipt.id))
                        }
                    )
                }
            }
        }
    }
}

// ==========================================
// КОМПОНЕНТЫ UI (Вынес в тот же файл для удобства)
// ==========================================

@Composable
fun FeaturedReceiptCard(
    receipt: TypicalReceipt,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(Dimen.large))
            .iosClickable { onClick() }
    ) {
        Image(
            painter = painterResource(receipt.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Градиент для читаемости текста
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

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimen.large)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "РЕКОМЕНДУЕМ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
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

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${receipt.timeMinutes} мин",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${receipt.calories} ккал",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}

@Composable
fun ReceiptListItem(
    receipt: TypicalReceipt,
    onClick: () -> Unit
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
                .height(180.dp)
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

        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(
                text = stringResource(receipt.titleRes),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(receipt.descRes),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ReceiptMetaChip(
                    icon = Icons.Default.Schedule,
                    text = "${receipt.timeMinutes} мин"
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
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}