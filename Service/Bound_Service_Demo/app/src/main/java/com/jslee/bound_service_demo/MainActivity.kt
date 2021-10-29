package com.jslee.bound_service_demo

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.jslee.bound_service_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var mRandomNumberGeneratorService: RandomNumberGeneratorService ?= null
    private lateinit var mServiceConnection: ServiceConnection
    private var mIsBound = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
                Log.e(LOG_TAG, "Service Connected")
                val mRandomNumberGeneratorServiceBinder = binder as RandomNumberGeneratorService.RandomNumberGeneratorServiceBinder
                mRandomNumberGeneratorService = mRandomNumberGeneratorServiceBinder.service
            }

            override fun onServiceDisconnected(componentName: ComponentName?) {
                Log.e(LOG_TAG, "Service Disconnected")
                mRandomNumberGeneratorService = null
            }
        }

        bindView()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindSafely()
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        binding.bindServiceButton.setOnClickListener {
            if (!mIsBound) {
                Toast.makeText(this, "Bind Service Success", Toast.LENGTH_LONG).show()
                val intent = Intent(this, RandomNumberGeneratorService::class.java)
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
                mIsBound = true
            }
        }

        binding.unbindServiceButton.setOnClickListener {
            Toast.makeText(this, "Unbind Service Success", Toast.LENGTH_LONG).show()
            unbindSafely()
        }

        binding.generateNumberButton.setOnClickListener {
            if (!mIsBound) {
                binding.randomNumberTextView.text = "Service Not Bound"
            } else {
                binding.randomNumberTextView.text = mRandomNumberGeneratorService?.randomNumber.toString()
            }

        }

    }

    private fun unbindSafely() {
        if (mIsBound) {
            unbindService(mServiceConnection)
            mIsBound = false
        }

    }

    companion object {
        private const val LOG_TAG = "MAIN_ACTIVITY"
    }
}