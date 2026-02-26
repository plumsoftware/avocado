package ru.plumsoftware.avocado.ui.screen.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.data.onboarding.UserGoal
import ru.plumsoftware.avocado.data.onboarding.UserRestriction
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.modifier.iosClickable

// Цвета для Онбординга (можно вынести в Theme)
val IOSGreen = Color(0xFF5E8C31)
val IOSGrayBg = Color(0xFFF2F2F7) // System Gray 6
val IOSLightGray = Color(0xFFE5E5EA) // System Gray 5 (для неактивных чипсов)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: (List<UserGoal>, List<UserRestriction>) -> Unit
) {
    val selectedGoals = remember { mutableStateListOf<UserGoal>() }
    val selectedRestrictions = remember { mutableStateListOf<UserRestriction>() }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    // Фон с затемнением
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        // "Лист" (Sheet)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.92f) // Занимает 92% высоты (как открытый sheet)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White) // Всегда белый фон для iOS стиля
        ) {
            // 1. Grabber (Ручка)
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(40.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFC7C7CC)) // System Gray 4
                    .align(Alignment.CenterHorizontally)
            )

            // 2. Контент (Pager)
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> WelcomePage()
                    1 -> GoalsPage(selectedGoals)
                    2 -> RestrictionsPage(selectedRestrictions)
                }
            }

            // 3. Нижняя панель (Индикаторы + Кнопка)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp, top = 16.dp), // Большой отступ снизу (как на iPhone)
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Индикатор страниц (Dots)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
                        val isSelected = pagerState.currentPage == index
                        // Анимация цвета точек
                        val color by animateColorAsState(
                            if (isSelected) IOSGreen else Color(0xFFC7C7CC),
                            label = "dot_color"
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }

                // Большая кнопка "Continue"
                val isLastPage = pagerState.currentPage == 2

                Button(
                    onClick = {
                        scope.launch {
                            if (isLastPage) {
                                onFinish(selectedGoals, selectedRestrictions)
                            } else {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp), // Высокая кнопка (iOS standard)
                    shape = RoundedCornerShape(14.dp), // Squircle shape
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IOSGreen,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp) // Без тени (Flat)
                ) {
                    Text(
                        text = if (isLastPage) "Начать" else "Продолжить",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// --- ЭКРАН 1: ПРИВЕТСТВИЕ ---
@Composable
fun WelcomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Авокадо (Заглушка)
        Box(
            modifier = Modifier
                .size(180.dp)
                .shadow(elevation = 20.dp, spotColor = IOSGreen.copy(0.3f), shape = CircleShape)
                .background(Color.White, CircleShape)
                .border(6.dp, Color(0xFFF2F2F7), CircleShape), // Рамка
            contentAlignment = Alignment.Center
        ) {
            // Тут должна быть твоя картинка
            Image(
                painter = painterResource(R.drawable.apple), // Убедись, что картинка есть!
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Привет, я Аво!",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Твой личный помощник по здоровому питанию. Я подберу идеальный рацион для твоих целей.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
                lineHeight = 24.sp
            ),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// --- ЭКРАН 2: ЦЕЛИ ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoalsPage(selectedGoals: MutableList<UserGoal>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Скролл, если целей много
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Твоя цель?",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.Black
        )
        Text(
            text = "Выбери одну или несколько",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            UserGoal.values().forEach { goal ->
                IOSSelectionChip(
                    text = stringResource(goal.titleRes),
                    isSelected = selectedGoals.contains(goal),
                    onClick = { if (selectedGoals.contains(goal)) selectedGoals.remove(goal) else selectedGoals.add(goal) }
                )
            }
        }
    }
}

// --- ЭКРАН 3: ИСКЛЮЧЕНИЯ ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RestrictionsPage(selectedRestrictions: MutableList<UserRestriction>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Есть ограничения?",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.Black
        )
        Text(
            text = "Мы скроем неподходящие продукты",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            UserRestriction.values().forEach { restriction ->
                IOSSelectionChip(
                    text = stringResource(restriction.titleRes),
                    isSelected = selectedRestrictions.contains(restriction),
                    onClick = {
                        if (selectedRestrictions.contains(restriction))
                            selectedRestrictions.remove(restriction)
                        else
                            selectedRestrictions.add(restriction)
                    }
                )
            }
        }
    }
}

// --- IOS STYLE CHIP ---
@Composable
fun IOSSelectionChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Анимация цвета фона
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) IOSGreen else IOSLightGray,
        animationSpec = tween(300),
        label = "bg"
    )

    // Анимация цвета текста
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
        animationSpec = tween(300),
        label = "text"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp)) // Мягкий квадрат (Squircle-like)
            .background(backgroundColor)
            .iosClickable(
                onClick = {
                    onClick()
                }
            )
            .padding(horizontal = 20.dp, vertical = 14.dp) // Больше воздуха внутри
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold, // Жирный текст
                fontSize = 15.sp
            ),
            color = textColor
        )
    }
}