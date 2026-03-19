package ru.plumsoftware.avocado.ui.screen.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import ru.plumsoftware.avocado.ui.theme.Dimen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.ui.platform.LocalContext

// Цвета (лучше тоже вынести в Color.kt, но пока здесь)
val IOSGreen = Color(0xFF5E8C31)
val IOSGrayBg = Color(0xFFF2F2F7)
val IOSLightGray = Color(0xFFE5E5EA)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: (List<UserGoal>, List<UserRestriction>) -> Unit,
    onPrivacyClick: () -> Unit
) {
    val selectedGoals = remember { mutableStateListOf<UserGoal>() }
    val selectedRestrictions = remember { mutableStateListOf<UserRestriction>() }
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .clip(RoundedCornerShape(topStart = Dimen.large, topEnd = Dimen.large))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = Dimen.mediumAboveHalf)
                    .width(Dimen.grabberWidth)
                    .height(Dimen.grabberHeight)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFC7C7CC))
                    .align(Alignment.CenterHorizontally)
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                userScrollEnabled = pagerState.currentPage > 0
            ) { page ->
                when (page) {
                    0 -> WelcomePage(onPrivacyClick = onPrivacyClick)
                    1 -> GoalsPage(selectedGoals)
                    2 -> RestrictionsPage(selectedRestrictions)
                    3 -> NotificationsPage()
                }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = Dimen.large)
                    .padding(bottom = 48.dp, top = Dimen.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimen.large)
            ) {
                // Точки
                Row(horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)) {
                    // 🔥 ИЗМЕНЕНО: repeat(4)
                    repeat(4) { index ->
                        val isSelected = pagerState.currentPage == index
                        val color by animateColorAsState(
                            if (isSelected) IOSGreen else Color(0xFFC7C7CC),
                            label = "dot"
                        )
                        Box(
                            modifier = Modifier
                                .size(Dimen.mediumHalf)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }

                // 🔥 ИЗМЕНЕНО: Последняя страница теперь 3
                val isLastPage = pagerState.currentPage == 3
                val buttonText = when (pagerState.currentPage) {
                    0 -> stringResource(R.string.onboarding_btn_read)
                    1, 2 -> stringResource(R.string.onboarding_btn_continue)
                    3 -> stringResource(R.string.onboarding_btn_start)
                    else -> stringResource(R.string.onboarding_btn_continue)
                }

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
                        .height(Dimen.buttonHeight),
                    shape = RoundedCornerShape(Dimen.buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IOSGreen,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// --- НОВЫЙ ЭКРАН: УВЕДОМЛЕНИЯ ---
@Composable
fun NotificationsPage() {
    val context = LocalContext.current

    // Проверяем, дано ли разрешение (для Android 13+)
    var isGranted by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true // На старых Android разрешения даются при установке
            }
        )
    }

    // Лаунчер для запроса разрешения
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            isGranted = granted
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Красивая иконка колокольчика
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(Color(0xFFFFF3E0), CircleShape), // Мягкий оранжевый фон
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = null,
                tint = Color(0xFFFF9800), // Яркий оранжевый
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.onboarding_notif_title),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))

        Text(
            text = stringResource(R.string.onboarding_notif_subtitle),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
                lineHeight = 24.sp
            ),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Кнопка запроса разрешения (внутри слайда)
        if (isGranted) {
            // Если уже разрешил (или Android < 13)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = IOSGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.onboarding_notif_granted),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = IOSGreen)
                )
            }
        } else {
            // Кнопка в стиле iOS "Secondary Action"
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimen.chipCornerRadius))
                    .background(Color(0xFFE5E5EA)) // System Gray 5
                    .iosClickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_btn_allow_notif),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF007AFF) // iOS Blue Link Color
                    )
                )
            }
        }
    }
}

@Composable
fun WelcomePage(onPrivacyClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.avo_welcome),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.onboarding_welcome_title),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimen.mediumAboveHalf))

        Text(
            text = stringResource(R.string.onboarding_welcome_text),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 17.sp,
                lineHeight = 24.sp
            ),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // --- ВСТАВЛЯЕМ ПЛАШКУ ЗДЕСЬ ---
        Spacer(modifier = Modifier.height(32.dp))
        OnboardingDisclaimer()

        Spacer(modifier = Modifier.height(16.dp))

        // --- ССЫЛКА НА ПОЛИТИКУ КОНФИДЕНЦИАЛЬНОСТИ ---
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.onboarding_terms_prefix),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Text(
                text = stringResource(R.string.onboarding_terms_link),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF007AFF), // iOS Blue
                modifier = Modifier
                    .padding(top = 2.dp)
                    .iosClickable { onPrivacyClick() }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoalsPage(selectedGoals: MutableList<UserGoal>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.large, vertical = Dimen.extraLarge)
    ) {
        Text(
            text = stringResource(R.string.onboarding_goals_title),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.onboarding_goals_subtitle),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = Dimen.mediumHalf, bottom = Dimen.extraLarge)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
            verticalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)
        ) {
            UserGoal.values().forEach { goal ->
                IOSSelectionChip(
                    text = stringResource(goal.titleRes),
                    iconRes = goal.iconRes,
                    isSelected = selectedGoals.contains(goal),
                    onClick = {
                        if (selectedGoals.contains(goal)) selectedGoals.remove(goal)
                        else selectedGoals.add(goal)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimen.extraLarge))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RestrictionsPage(selectedRestrictions: MutableList<UserRestriction>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.large, vertical = Dimen.extraLarge)
    ) {
        Text(
            text = stringResource(R.string.onboarding_restrictions_title),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.onboarding_restrictions_subtitle),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = Dimen.mediumHalf, bottom = Dimen.extraLarge)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Dimen.mediumHalf),
            verticalArrangement = Arrangement.spacedBy(Dimen.mediumHalf)
        ) {
            UserRestriction.values().forEach { restriction ->
                IOSSelectionChip(
                    text = stringResource(restriction.titleRes),
                    iconRes = null,
                    isSelected = selectedRestrictions.contains(restriction),
                    onClick = {
                        if (selectedRestrictions.contains(restriction)) selectedRestrictions.remove(restriction)
                        else selectedRestrictions.add(restriction)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimen.extraLarge))
    }
}

@Composable
fun IOSSelectionChip(
    text: String,
    iconRes: Int? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) IOSGreen else IOSLightGray,
        animationSpec = tween(300), label = "bg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
        animationSpec = tween(300), label = "content"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimen.chipCornerRadius))
            .background(backgroundColor)
            .iosClickable { onClick() }
            .padding(horizontal = Dimen.medium, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (iconRes != null) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(Dimen.iconSizeSmall),
            )
            Spacer(modifier = Modifier.width(Dimen.mediumHalf))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            ),
            color = contentColor
        )
    }
}

@Composable
fun OnboardingDisclaimer() {
    // Стиль iOS Footnote: очень сдержанный, серый, маленький
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.medium)
            .background(
                color = Color(0xFFF2F2F7),
                shape = RoundedCornerShape(Dimen.mediumAboveHalf)
            )
            .padding(Dimen.mediumAboveHalf),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Иконка AI (звездочки)
        Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = "AI Generated",
            tint = Color(0xFF8E8E93), // System Gray
            modifier = Modifier.size(Dimen.medium)
        )

        Spacer(modifier = Modifier.width(Dimen.mediumHalf))

        Text(
            text = stringResource(R.string.onboarding_ai_disclaimer),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF8E8E93),
                lineHeight = 14.sp,
                textAlign = TextAlign.Start
            )
        )
    }
}