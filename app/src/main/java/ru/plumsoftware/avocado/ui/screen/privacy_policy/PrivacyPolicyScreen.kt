package ru.plumsoftware.avocado.ui.screen.privacy_policy

import android.graphics.Color as AndroidColor
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import ru.plumsoftware.avocado.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val htmlText = stringResource(R.string.privacy_policy_html)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.privacy_policy_title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // AndroidView для рендера HTML
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    TextView(context).apply {
                        movementMethod = LinkMovementMethod.getInstance() // Делает ссылки кликабельными
                        textSize = 16f
                        // Настраиваем цвета ссылок
                        setLinkTextColor(if (isDark) AndroidColor.parseColor("#0A84FF") else AndroidColor.parseColor("#007AFF"))
                    }
                },
                update = { textView ->
                    // Конвертируем HTML-строку в форматированный текст
                    textView.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    // Адаптируем цвет текста под светлую/темную тему
                    textView.setTextColor(if (isDark) AndroidColor.WHITE else AndroidColor.BLACK)
                }
            )
        }
    }
}