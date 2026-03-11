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
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.plumsoftware.avocado.MainActivity
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.notification.NotificationArgs
import ru.plumsoftware.avocado.data.notification.NotificationContentHelper

class DailyNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Проверяем разрешение (Android 13+ требует явного разрешения)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return Result.failure() // Отменяем работу, если юзер запретил пуши
            }
        }

        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val channelId = "avocado_daily_channel"
        val notificationId = System.currentTimeMillis().toInt()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 1. Создание канала (Обязательно для Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notif_channel_name)
            val descriptionText = context.getString(R.string.notif_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        // 3. Получаем динамический контент
        // Получаем контент СНАЧАЛА, потому что нам нужен route для Intent
        val content = NotificationContentHelper.getDailyContent(context)

        // Интент по клику
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // 🔥 КЛАДЕМ МАРШРУТ В ПОСЫЛКУ
            putExtra(NotificationArgs.DESTINATION_ROUTE, content.targetRoute)
        }

        // ВАЖНО: используем FLAG_UPDATE_CURRENT, чтобы посылка (Extra) не потерялась
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 4. Сборка уведомления
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apple)
            .setContentTitle(content.title)
            .setContentText(content.body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content.body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Скрыть после того как юзер нажал
            .setColor(ContextCompat.getColor(context, R.color.black))

        // 5. Показ
        with(NotificationManagerCompat.from(context)) {
            // Проверка разрешения уже была в doWork, так что подавляем варнинг
            try {
                notify(notificationId, builder.build())
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }
}