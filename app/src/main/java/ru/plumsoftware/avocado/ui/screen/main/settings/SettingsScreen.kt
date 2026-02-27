package ru.plumsoftware.avocado.ui.screen.main.settings

import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.ui.getBottomInsetInDp
import ru.plumsoftware.avocado.ui.getTopInsetInDp
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.settings.elements.IOSSettingsItem
import ru.plumsoftware.avocado.ui.theme.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val context = LocalContext.current
    val activity = LocalActivity.current

    // Получаем версию приложения
    val appVersion = try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        context.getString(R.string.settings_version_format, pInfo.versionName, pInfo.versionCode)
    } catch (e: Exception) {
        context.getString(R.string.settings_version_fallback)
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 14.dp),
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = Dimen.medium)
        ) {

            Text(
                text = stringResource(R.string.settings_section_personalization),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
            )

            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                // Кнопка для повторного открытия Онбординга
                IOSSettingsNavigationItem(
                    title = stringResource(R.string.settings_item_goals),
                    icon = Icons.Default.RestaurantMenu, // Или любая иконка
                    onClick = {
                        navController.navigate(AppDestination.Onboarding)
                    },
                    showDivider = false
                )
            }

            Spacer(modifier = Modifier.height(Dimen.large))

            // ЗАГОЛОВОК СЕКЦИИ
            Text(
                text = stringResource(R.string.settings_section_appearance),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
            )

            // БЛОК НАСТРОЕК (Скругленный контейнер)
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                IOSSettingsItem(
                    title = stringResource(R.string.settings_theme_system),
                    isSelected = currentTheme == AppTheme.System,
                    onClick = { viewModel.updateTheme(AppTheme.System) }
                )
                IOSSettingsItem(
                    title = stringResource(R.string.settings_theme_light),
                    isSelected = currentTheme == AppTheme.Light,
                    onClick = { viewModel.updateTheme(AppTheme.Light) }
                )
                IOSSettingsItem(
                    title = stringResource(R.string.settings_theme_dark),
                    isSelected = currentTheme == AppTheme.Dark,
                    onClick = { viewModel.updateTheme(AppTheme.Dark) },
                    showDivider = false
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxWidth().padding(bottom = 100.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = appVersion,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun IOSSettingsNavigationItem(
    title: String,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .iosClickable { onClick() }
            .padding(start = Dimen.medium)
    ) {
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