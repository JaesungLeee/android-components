package com.jslee.foreground_service_music_player

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jslee.foreground_service_music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindView()
    }

    private fun bindView() {
        binding.startServiceButton.setOnClickListener {
            val intent = Intent(this, MusicPlayService::class.java).apply {
                action = UserActions.START_FOREGROUND
            }
            startService(intent)
        }

        binding.stopServiceButton.setOnClickListener {
            val intent = Intent(this, MusicPlayService::class.java).apply {
                action = UserActions.STOP_FOREGROUND
            }
            startService(intent)
        }
    }
}