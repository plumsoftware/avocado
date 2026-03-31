package ru.plumsoftware.avocado.ui.screen.main.settings

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.ui.getBottomInsetInDp
import ru.plumsoftware.avocado.ui.getTopInsetInDp
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.settings.elements.IOSSettingsItem
import ru.plumsoftware.avocado.ui.screen.main.settings.elements.IOSSettingsSwitchItem
import ru.plumsoftware.avocado.ui.theme.Dimen
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


@SuppressLint("BatteryLife")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val context = LocalContext.current

    // --- 1. ПРОВЕРКА УВЕДОМЛЕНИЙ (Реактивная) ---
    var hasNotifications by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    val notifLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> hasNotifications = isGranted }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // Обновляем статус свитча каждый раз, когда юзер возвращается на экран
                hasNotifications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else true
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // --- 2. ПРОВЕРКА ФОНОВОГО РЕЖИМА (Батарея) ---
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    var hasBackgroundWork by remember {
        mutableStateOf(powerManager.isIgnoringBatteryOptimizations(context.packageName))
    }

    val backgroundLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        hasBackgroundWork = powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    // --- 3. ВЕРСИЯ ПРИЛОЖЕНИЯ ---
    val appVersion = try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        context.getString(R.string.settings_version_format, pInfo.versionName, pInfo.versionCode)
    } catch (e: Exception) {
        context.getString(R.string.settings_version_fallback)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // В iOS заголовок "Настройки" обычно крупный (Large Title) или по центру.
            // Оставим твой CenterAlignedTopAppBar, но сделаем его прозрачным,
            // чтобы градиент под ним красиво размывал список.
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Прозрачный фон
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {

            // --- ОСНОВНОЙ КОНТЕНТ (Скроллящийся) ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimen.medium),
                // Отступы: сверху чтобы не перекрывать TopBar, снизу для BottomNav
            ) {
                // Отступ от TopBar
                Spacer(modifier = Modifier.height(padding.calculateTopPadding() + Dimen.medium))

                // --- ПЕРСОНАЛИЗАЦИЯ ---
                Text(
                    text = stringResource(R.string.settings_section_personalization),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimen.large))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IOSSettingsNavigationItem(
                        title = stringResource(R.string.settings_item_goals),
                        icon = Icons.Default.RestaurantMenu,
                        onClick = { navController.navigate(AppDestination.Onboarding) },
                        showDivider = false
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.large))

                // --- РАЗРЕШЕНИЯ ---
                Text(
                    text = stringResource(R.string.settings_section_permissions),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimen.large))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IOSSettingsSwitchItem(
                        title = stringResource(R.string.settings_item_push),
                        isChecked = hasNotifications,
                        onCheckedChange = { turnOn ->
                            if (turnOn) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            } else {
                                val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                    }
                                } else {
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                }
                                context.startActivity(intent)
                            }
                        },
                        showDivider = true
                    )

                    IOSSettingsSwitchItem(
                        title = stringResource(R.string.settings_item_background),
                        isChecked = hasBackgroundWork,
                        onCheckedChange = { turnOn ->
                            if (turnOn) {
                                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                    data = Uri.parse("package:${context.packageName}")
                                }
                                backgroundLauncher.launch(intent)
                            } else {
                                val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                                context.startActivity(intent)
                            }
                        },
                        showDivider = false
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.large))

                // --- ВНЕШНИЙ ВИД ---
                Text(
                    text = stringResource(R.string.settings_section_appearance),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimen.large))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IOSSettingsItem(
                        title = stringResource(R.string.settings_theme_system),
                        isSelected = currentTheme == AppTheme.System,
                        onClick = { viewModel.updateTheme(AppTheme.System) })
                    IOSSettingsItem(
                        title = stringResource(R.string.settings_theme_light),
                        isSelected = currentTheme == AppTheme.Light,
                        onClick = { viewModel.updateTheme(AppTheme.Light) })
                    IOSSettingsItem(
                        title = stringResource(R.string.settings_theme_dark),
                        isSelected = currentTheme == AppTheme.Dark,
                        onClick = { viewModel.updateTheme(AppTheme.Dark) },
                        showDivider = false
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.large))

                // --- О ПРИЛОЖЕНИИ ---
                Text(
                    text = stringResource(R.string.settings_section_about),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimen.large))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IOSSettingsNavigationItem(
                        title = stringResource(R.string.privacy_policy_title),
                        icon = Icons.Default.Info,
                        onClick = { navController.navigate(AppDestination.PrivacyPolicy) },
                        showDivider = false
                    )
                }

                // Отступ перед версией
                Spacer(modifier = Modifier.height(Dimen.extraLarge))

                // --- ВЕРСИЯ ПРИЛОЖЕНИЯ ---
                Text(
                    text = appVersion,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 120.dp), // Место под нижний BottomBar
                    textAlign = TextAlign.Center
                )
            }

            // --- 2. ВЕРХНИЙ ГРАДИЕНТ (Под TopBar) ---
            // Рисуется поверх списка, но под TopBar. Дает эффект стекла при скролле.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    // Высота градиента = Высота TopBar + немного заступа
                    .height(padding.calculateTopPadding() + 20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun IOSSettingsNavigationItem(
    title: String, icon: ImageVector? = null, onClick: () -> Unit, showDivider: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .iosClickable { onClick() }
            .padding(start = Dimen.medium)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimen.mediumAboveHalf)
                .padding(end = Dimen.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(Dimen.mediumAboveHalf))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Стрелочка "Вправо" (Chevron)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }

        if (showDivider) {
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
        }
    }
}