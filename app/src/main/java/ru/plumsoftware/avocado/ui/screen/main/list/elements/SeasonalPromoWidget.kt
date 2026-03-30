package ru.plumsoftware.avocado.ui.screen.main.list.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.rustore.SeasonProductsResponse
import ru.plumsoftware.avocado.ui.dataUrlToBitmap
import ru.plumsoftware.avocado.ui.screen.main.receipt.details.IngredientItem
import ru.plumsoftware.avocado.ui.theme.Dimen

@Composable
fun SeasonalPromoWidget(
    promo: SeasonProductsResponse,
    foods: List<Food>,
    onFoodClick: (String) -> Unit
) {
    // Декодируем картинку 1 раз при рекомпозиции
    val bitmap = remember(promo.promo) { dataUrlToBitmap(promo.promo) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimen.large)
    ) {
        // 1. БАННЕР С КАРТИНКОЙ И ЗАГОЛОВКОМ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(Dimen.large))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            // Картинка из Base64
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Градиент снизу для читаемости текста
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 150f
                        )
                    )
            )

            // Бейдж и Текст
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Dimen.medium)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "СЕЗОННОЕ ПРЕДЛОЖЕНИЕ",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.Black)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = promo.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = Color.White)
                )
            }
        }

        // 2. ПРОДУКТЫ (Карусель снизу)
        if (foods.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Dimen.medium))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(foods) { food ->
                    // Используем уже готовый компонент IngredientItem (из рецептов)
                    // или делаем маленькую карточку
                    IngredientItem(food = food) {
                        onFoodClick(food.id)
                    }
                }
            }
        }
    }
}