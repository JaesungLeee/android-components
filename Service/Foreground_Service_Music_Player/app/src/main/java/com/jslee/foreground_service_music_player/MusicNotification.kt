package com.jslee.foreground_service_music_player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object MusicNotification {

    private const val CHANNEL_ID = "FOREGROUND_SERVICE_CHANNEL"
    private const val INTENT_REQUEST_CODE = 0

    private lateinit var notificationIntent: Intent
    private lateinit var prevIntent: Intent
    private lateinit var playIntent: Intent
    private lateinit var nextIntent: Intent
    private lateinit var pendingIntent: PendingIntent
    private lateinit var playPendingIntent: PendingIntent
    private lateinit var prevPendingIntent: PendingIntent
    private lateinit var nextPendingIntent: PendingIntent

    private fun initIntent(context: Context) {
        notificationIntent = Intent(context, MainActivity::class.java).apply {
            action = UserActions.MAIN
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        prevIntent = Intent(context, MusicPlayService::class.java).apply {
            action = UserActions.PREV
        }

        playIntent = Intent(context, MusicPlayService::class.java).apply {
            action = UserActions.PLAY
        }

        nextIntent = Intent(context, MusicPlayService::class.java).apply {
            action = UserActions.NEXT
        }

        pendingIntent = PendingIntent.getActivity(context, INTENT_REQUEST_CODE, notificationIntent, FLAG_UPDATE_CURRENT)
        playPendingIntent = PendingIntent.getService(context, INTENT_REQUEST_CODE, playIntent, 0)
        prevPendingIntent = PendingIntent.getService(context, INTENT_REQUEST_CODE, prevIntent, 0)
        nextPendingIntent = PendingIntent.getService(context, INTENT_REQUEST_CODE, nextIntent, 0)
    }

    fun createNotification(context: Context): Notification {

        initIntent(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Foreground Service Music Player")
            .setContentText("Music")
            .setSmallIcon(R.drawable.ic_launcher_background).setOngoing(true)
            .addAction(NotificationCompat.Action(R.drawable.ic_media_previous_24, "Previous", prevPendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_media_play_24, "Play", playPendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_media_next_24, "Next", nextPendingIntent))
            .setContentIntent(pendingIntent)
            .build()

        return notification

    }
}