package com.jslee.broadcastreceiver_demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.jslee.broadcastreceiver_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var wifiSwitch: SwitchCompat
    lateinit var wifiManager: WifiManager

    private val wifiStatusReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // EXTRA_WIFI_STATE : Wi-Fi 상태 (enable, disable, enabling, disabling, unknown)
            // WIFI_STATE_UNKNOWN : Wi-Fi가 Unknown 상태

            when (intent?.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiSwitch.isChecked = true
                    wifiSwitch.text = "ON"
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiSwitch.isChecked = false
                    wifiSwitch.text = "OFF"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.e(LOG_TAG, "onCreate")

        wifiSwitch = binding.wifiSwitch
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        bindView()

        // receiver 등록
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStatusReceiver, intentFilter)


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(LOG_TAG, "onDestroy")

        // onCreate에서 register한 receiver 해지
        unregisterReceiver(wifiStatusReceiver)
    }

    private fun bindView() {
        wifiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                binding.wifiStatusTextView.text = "Wi-Fi ON"
            } else {
                wifiManager.isWifiEnabled = false
                binding.wifiStatusTextView.text = "Wi-Fi OFF"
            }
        }
    }

    companion object {
        private val LOG_TAG = "MainActivity"
    }
}