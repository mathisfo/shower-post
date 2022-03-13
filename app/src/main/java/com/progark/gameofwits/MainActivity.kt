package com.progark.gameofwits

import GameModule
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.databinding.ActivityMainBinding
import com.soywiz.korge.android.KorgeAndroidView


// This file is kinda a mess just :)
// Dere må lese om hvordan AndroidActivitiy fungere for denne delen
// Basically så leser den settingsene som ligger i main.kt
// Resten er native stuff
class MainActivity : AppCompatActivity() {

    private lateinit var korgeAndroidView: KorgeAndroidView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // val db = Firebase.firestore
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        korgeAndroidView = KorgeAndroidView(this)
        binding.toolContainer.addView(korgeAndroidView)

        binding.loadViewButton.setOnClickListener {
            binding.loadViewButton.isEnabled = false
            binding.unloadViewButton.isEnabled = true
            loadToolModule()
        }

        binding.unloadViewButton.setOnClickListener {
            binding.loadViewButton.isEnabled = true
            binding.unloadViewButton.isEnabled = false
            unloadToolModule()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.loadViewButton.isEnabled = true
        binding.unloadViewButton.isEnabled = false

    }

    override fun onPause() {
        super.onPause()
        unloadToolModule()
    }

    private fun loadToolModule() {
        korgeAndroidView.loadModule(GameModule)
    }

    private fun unloadToolModule() {
        korgeAndroidView.unloadModule()
    }
}