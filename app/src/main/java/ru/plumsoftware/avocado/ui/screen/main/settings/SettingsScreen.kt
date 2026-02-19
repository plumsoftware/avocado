package ru.plumsoftware.avocado.ui.screen.main.settings

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.user_preferences.util.AppTheme
import ru.plumsoftware.avocado.ui.getBottomInsetInDp
import ru.plumsoftware.avocado.ui.getTopInsetInDp
import ru.plumsoftware.avocado.ui.screen.main.settings.elements.IOSSettingsItem
import ru.plumsoftware.avocado.ui.theme.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val context = LocalContext.current
    val activity = LocalActivity.current

    // Получаем версию приложения
    val appVersion = try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        "${pInfo.versionName} (${pInfo.versionCode})"
    } catch (e: Exception) {
        "1.0.0"
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
                .padding(all = Dimen.medium)
        ) {

            // ЗАГОЛОВОК СЕКЦИИ
            Text(
                text = "Внешний вид",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = Dimen.medium, bottom = Dimen.mediumHalf)
            )

            // БЛОК НАСТРОЕК (Скругленный контейнер)
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium) // Скругление группы
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                IOSSettingsItem(
                    title = "Системная",
                    isSelected = currentTheme == AppTheme.System,
                    onClick = { viewModel.updateTheme(AppTheme.System) }
                )
                IOSSettingsItem(
                    title = "Светлая",
                    isSelected = currentTheme == AppTheme.Light,
                    onClick = { viewModel.updateTheme(AppTheme.Light) }
                )
                IOSSettingsItem(
                    title = "Темная",
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
                    text = "Avocado $appVersion",
                    style = MaterialTheme.typography.labelSmall, // Мелкий шрифт
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}