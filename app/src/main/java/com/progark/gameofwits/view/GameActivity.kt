package com.progark.gameofwits.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.progark.gameofwits.databinding.ActivityGameBinding
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