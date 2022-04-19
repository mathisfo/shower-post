package com.progark.gameofwits.view

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.progark.gameofwits.R
import com.progark.gameofwits.databinding.ActivityGameBinding
import com.progark.gameofwits.databinding.ActivityMainBinding
import com.progark.gameofwits.observers.PlayerEventSource
import com.progark.gameofwits.viewmodel.GameViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerEventSource.addObserver(gameViewModel)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lobbyId = intent.getStringExtra("ACTIVE_LOBBY_ID")!!
        val userId = intent.getStringExtra("USER_ID")!!
        gameViewModel.fetchLobby(lobbyId)
        gameViewModel.setActiveUser(userId)
    }
}