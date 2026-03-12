package ru.plumsoftware.avocado.data.notification.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import ru.plumsoftware.avocado.MainActivity
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.notification.NotificationData

object DebugNotificationSender {

    fun sendTestNotifications(context: Context) {
        // Проверяем, есть ли разрешение (иначе приложение упадет на Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return // Разрешения еще нет (например, самый первый запуск до онбординга)
            }
        }

        val channelId = "avocado_debug_channel"

        // 1. Создаем канал с ВЫСОКОЙ важностью (чтобы пуши всплывали поверх экрана)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Debug Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // 2. Готовим 3 разных пуша с диплинками для проверки
        val testNotifications = listOf(
            NotificationData(
                title = "☀️ Утро (Test)",
                body = "Тестируем переход на рецепт тоста с авокадо",
                targetRoute = "receipt/avocado_toast"
            ),
            NotificationData(
                title = "🥦 День (Test)",
                body = "Тестируем переход на карточку брокколи",
                targetRoute = "food/broccoli"
            ),
            NotificationData(
                title = "🌙 Вечер (Test)",
                body = "Тестируем переход на запеченного лосося",
                targetRoute = "receipt/baked_salmon"
            )
        )

        // 3. Отправляем их все разом
        val notificationManager = NotificationManagerCompat.from(context)

        testNotifications.forEachIndexed { index, content ->
            // Уникальный ID для каждого пуша, чтобы они не перезаписывали друг друга
            val notifId = 9990 + index

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("DESTINATION_ROUTE", content.targetRoute)
            }

            // Важно: requestCode должен быть уникальным (здесь это index),
            // иначе Интенты "склеятся" и все пуши будут открывать один и тот же экран
            val pendingIntent = PendingIntent.getActivity(
                context,
                index,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.apple)
                .setContentTitle(content.title)
                .setContentText(content.body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content.body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.primary_color))
                .setAutoCancel(true)

            try {
                notificationManager.notify(notifId, builder.build())
            } catch (e: SecurityException) {
                // Игнорируем, если нет прав
            }
        }
    }
}