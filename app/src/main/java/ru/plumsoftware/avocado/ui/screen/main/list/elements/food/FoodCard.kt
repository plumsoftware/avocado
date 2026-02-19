package ru.plumsoftware.avocado.ui.screen.main.list.elements.food

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.protobuf.copy
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.food.Food
import ru.plumsoftware.avocado.data.base.model.food.FoodType
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.main.list.HarmoniousColors
import ru.plumsoftware.avocado.ui.screen.main.list.getLightenedColor
import ru.plumsoftware.avocado.ui.theme.Dimen
import ru.plumsoftware.avocado.ui.theme.vitaminColor

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun FoodCard(
    item: Food,
    isFavorite: Boolean = false, // Приходит из ViewModel
    onLikeClick: () -> Unit = {},
    onGetColor: (Int, Context) -> Int,
    modifier: Modifier
) {
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

    // Генерируем цвета (твоя логика + оптимизация)
    val dominantColorInt = remember(item.imageRes) { onGetColor(item.imageRes, context) }
    val imageBackgroundInt =
        remember(dominantColorInt) { getLightenedColor(dominantColorInt, factor = 0.85f) }
    // Цвет текста "Ккал" (чуть темнее фона)
    val badgeColor =
        remember(dominantColorInt) { getLightenedColor(dominantColorInt, factor = 0.4f) }

    val dominantColor = Color(dominantColorInt)
    val imageBackground = Color(imageBackgroundInt)

    // Константы размеров
    val cardShape = RoundedCornerShape(20.dp) // iOS любит 20-22dp
    val imageSize = 100.dp

    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = cardShape,
                spotColor = Color.Black.copy(alpha = 0.05f),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(cardShape)
            .background(Color.White)
            .iosClickable {

            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
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
                        .size(imageSize)
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    painter = painterResource(item.imageRes),
                    contentDescription = stringResource(item.titleRes),
                    contentScale = ContentScale.FillBounds
                )

                // Кнопка Лайка (Справа сверху)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                        .iosClickable { onLikeClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isFavorite) Color(0xFFFF3B30) else Color.Gray.copy(0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // 2. НИЖНЯЯ ЧАСТЬ: ИНФОРМАЦИЯ
            Column(
                modifier = Modifier
                    .padding(Dimen.mediumAboveHalf) // Внутренний отступ
            ) {
                // Название
                Text(
                    text = stringResource(item.titleRes),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.Black
                    ),
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(Dimen.extraSmall))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Калории
                    Text(
                        text = "${item.kpfc_100g.kals} ккал",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                    )

                    // Точка разделитель
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.LightGray
                    )

                    // Белки
                    if (item.foodType == FoodType.MUSHROOM || item.foodType == FoodType.NUT || item.foodType == FoodType.MEAT) {
                        if (item.kpfc_100g.proteins > 10) {
                            Text(
                                text = "${item.kpfc_100g.proteins.toInt()}г белка",
                                maxLines = 1,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF5E8C31)
                                )
                            )
                        }
                        // Омега
                    } else if (item.foodType == FoodType.FISH || item.foodType == FoodType.STRAWBERRY) {
                        if (item.kpfc_100g.omega3 >= 1.5) {
                            Text(
                                text = "${item.kpfc_100g.omega3.toInt()}г омега3",
                                maxLines = 1,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFD59B02)
                                )
                            )
                        }
                        // Клтчатка
                    } else if (item.foodType == FoodType.VEGETABLE)  {
                        Text(
                            text = "${item.kpfc_100g.fiber.toInt()}г клетчатки",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF5E8C31)
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        // Улеводы
                        Text(
                            text = "${item.kpfc_100g.carbohydrates.toInt()}г углеводов",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF5E8C31)
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}