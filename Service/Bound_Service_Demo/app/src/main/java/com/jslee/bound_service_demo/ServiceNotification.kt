package com.jslee.bound_service_demo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

object ServiceNotification {

    private lateinit var mNotificationManager: NotificationManager
    private const val INTENT_REQUEST_CODE = 0
    private const val NOTIFICATION_ID = 10
    private const val CHANNEL_ID = "RANDOM_NUMBER_BOUND_SERVICE"


    private fun showNotification(notification: Notification) {
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun hideNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID)
    }

    fun buildNotification(context: Context) {
        mNotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Random Number Generate Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        val contentIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, INTENT_REQUEST_CODE, contentIntent, 0)
        val notificationText = "service started.."

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Random Number Generate Bound Service")
            .setContentText(notificationText)
            .setSmallIcon(android.R.drawable.btn_dialog)
            .setTicker(notificationText)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)

        showNotification(notification.build())

    }
}