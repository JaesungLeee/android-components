package com.jslee.bound_service_demo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log

class RandomNumberGeneratorService: Service() {
    private val mBinder: IBinder = RandomNumberGeneratorServiceBinder()
    private lateinit var mHandler: Handler
    private lateinit var mHandlerThread: HandlerThread

    private var mIsGenerating = false
    var randomNumber: Int = -1

    inner class RandomNumberGeneratorServiceBinder: Binder() {
        val service: RandomNumberGeneratorService = this@RandomNumberGeneratorService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(LOG_TAG, "onStartCommand")
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e(LOG_TAG, "onBind")
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mHandlerThread = HandlerThread(HANDLER_THREAD_NAME)
        mHandlerThread.start()
        mHandler = Handler(mHandlerThread.looper)
        mHandler.post {
            generateRandomNumber()
        }
        ServiceNotification.buildNotification(this)

        Log.e(LOG_TAG, "Bound Service Created in ${mHandlerThread.threadId}")

    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
        mHandler.looper.quit()
        mHandlerThread.quit()
        ServiceNotification.hideNotification()
    }

    private fun generateRandomNumber() {
        mIsGenerating = true
        while (mIsGenerating) {
            Thread.sleep(1000)
            randomNumber = (Math.random()*100).toInt()
        }

    }

    companion object {
        private const val LOG_TAG = "RANDOM_NUMBER_GENERATOR_SERVICE"
        private const val HANDLER_THREAD_NAME = "RANDOM_GENERATE_NUMBER_THREAD"
    }
}