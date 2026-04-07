package ru.plumsoftware.avocado.ui.screen.main.receipt.elements

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MicOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen
import java.util.Locale

@Composable
fun VoiceAssistantWidget(
    isSpeaking: Boolean,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onRepeat: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 1. СОСТОЯНИЯ
    var hasMicPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        )
    }

    // 🔥 НОВОЕ: Состояние паузы, которую ставит сам пользователь (нажатием или голосом)
    var isUserPaused by remember { mutableStateOf(false) }

    val micLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        hasMicPermission = isGranted
        if (isGranted) isUserPaused = false // Если только что разрешили, сразу включаем
    }

    // 🔥 Активно слушаем, если: есть права И диктор молчит И пользователь не нажал "пауза"
    val isListeningActive = hasMicPermission && !isSpeaking && !isUserPaused

    // 2. АНИМАЦИЯ ПУЛЬСАЦИИ
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isListeningActive) 1.2f else 1f, // Пульсирует только когда РЕАЛЬНО слушает
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // 3. ЛОГИКА РАСПОЗНАВАНИЯ РЕЧИ
    DisposableEffect(isListeningActive) {
        var speechRecognizer: SpeechRecognizer? = null

        if (isListeningActive) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            }

            val listener = object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    scope.launch {
                        delay(500)
                        try { speechRecognizer?.startListening(intent) } catch (e: Exception) {}
                    }
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val text = matches?.firstOrNull()?.lowercase(Locale.getDefault()) ?: ""

                    // 🔥 ОБНОВЛЕННЫЕ КОМАНДЫ (Добавлены синонимы и СТОП)
                    when {
                        text.contains("дальше") || text.contains("вперед") || text.contains("следующий") || text.contains("продолжай") -> onNext()
                        text.contains("назад") || text.contains("предыдущий") || text.contains("вернись") -> onBack()
                        text.contains("повтори") || text.contains("еще раз") || text.contains("прочитай") -> onRepeat()
                        text.contains("стоп") || text.contains("хватит") || text.contains("пауза") || text.contains("замолчи") -> {
                            // Юзер сказал "Стоп" - ставим на паузу
                            isUserPaused = true
                        }
                    }

                    scope.launch {
                        delay(500)
                        // Запускаем снова, только если мы всё еще активны (юзер не сказал СТОП)
                        if (!isUserPaused) {
                            try { speechRecognizer?.startListening(intent) } catch (e: Exception) {}
                        }
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            }

            speechRecognizer.setRecognitionListener(listener)
            try { speechRecognizer.startListening(intent) } catch (e: Exception) {}
        }

        onDispose {
            speechRecognizer?.stopListening()
            speechRecognizer?.destroy()
        }
    }

    // 4. UI КОМПОНЕНТ
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimen.large))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(Dimen.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (hasMicPermission) {
            // --- МИКРОФОН РАЗРЕШЕН ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                // 🔥 ДЕЛАЕМ БЛОК КЛИКАБЕЛЬНЫМ ДЛЯ РУЧНОЙ ПАУЗЫ
                modifier = Modifier
                    .weight(1f)
                    .iosClickable { isUserPaused = !isUserPaused }
            ) {
                // Визуальное состояние иконки (Зеленая если слушает, Серая если спит/пауза)
                val isMicGreen = !isUserPaused && !isSpeaking

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scale) // Пульсирует
                        .clip(CircleShape)
                        .background(if (isMicGreen) IOSGreen.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isUserPaused || isSpeaking) Icons.Rounded.MicOff else Icons.Rounded.Mic,
                        contentDescription = null,
                        tint = if (isMicGreen) IOSGreen else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(Dimen.medium))

                Column {
                    Text(
                        text = stringResource(R.string.voice_assistant_title),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // 🔥 ДИНАМИЧЕСКИЙ ТЕКСТ ПОДСКАЗКИ
                    val hintText = when {
                        isUserPaused -> stringResource(R.string.voice_assistant_paused_hint)
                        isSpeaking -> stringResource(R.string.voice_assistant_speaking)
                        else -> stringResource(R.string.voice_assistant_hint)
                    }

                    Text(
                        text = hintText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            // --- ЗАПРОС РАЗРЕШЕНИЯ ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.MicOff, contentDescription = null, tint = Color.Gray)
                }
                Spacer(modifier = Modifier.width(Dimen.mediumHalf))
                Column {
                    Text(
                        text = stringResource(R.string.voice_assistant_title),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = stringResource(R.string.voice_assistant_turn_on_mic), // Из ресурсов
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            Button(
                onClick = { micLauncher.launch(Manifest.permission.RECORD_AUDIO) },
                colors = ButtonDefaults.buttonColors(containerColor = IOSGreen),
                shape = RoundedCornerShape(Dimen.medium),
                contentPadding = PaddingValues(horizontal = Dimen.mediumAboveHalf, vertical = Dimen.mediumHalf),
                modifier = Modifier.wrapContentSize()
            ) {
                Text(stringResource(R.string.voice_btn_allow), fontSize = 12.sp)
            }
        }
    }
}