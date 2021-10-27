package com.jslee.foreground_service_music_player

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MusicPlayService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(LOG_TAG, "RECEIVED - ${intent?.action}")

        when(intent?.action) {
            UserActions.START_FOREGROUND -> {
                Log.e(LOG_TAG, "GET_START_FOREGROUND")
                startForegroundService()
            }

            UserActions.STOP_FOREGROUND -> {
                Log.e(LOG_TAG, "GET_STOP_FOREGROUND")
                stopForegroundService()
            }
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        val notification = MusicNotification.createNotification(this)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(LOG_TAG, "ON_CREATE")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(LOG_TAG, "ON_DESTROY")
    }

    companion object {
        private const val LOG_TAG = "MUSIC_PLAY_SERVICE"
        private const val NOTIFICATION_ID = 20
    }
}