package com.progark.gameofwits.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.progark.gameofwits.model.Player
import com.progark.gameofwits.viewmodel.GameViewModel
import com.progark.gameofwits.R
import com.progark.gameofwits.observers.PlayerEventSource
import com.progark.gameofwits.view.adapters.PlayerAdapter
import com.progark.gameofwits.viewmodel.LobbyViewModel
import model.User

class LobbyView() : AppCompatActivity() {

    val lobbyViewModel: LobbyViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobbyview)
        PlayerEventSource.addObserver(lobbyViewModel)

        val lobbyId = intent.getStringExtra("ACTIVE_LOBBY_ID")!!
        val userId = intent.getStringExtra("USER_ID")!!


        val text: TextView = findViewById(R.id.lobbyId)
        // Set button click
        val btn: Button = findViewById(R.id.nextbutton)
        btn.setOnClickListener { openGameView() }

        val players = mutableListOf<User>()
        val playerList: ListView = findViewById(R.id.playerList)
        val adapter = PlayerAdapter(this, players)
        playerList.adapter = adapter

        lobbyViewModel.fetchLobby(lobbyId)
        lobbyViewModel.lobby.observe(this) { lobby ->
            text.text = lobby.pin
            lobby.players.forEach { player ->
                if (!players.contains(player)) players.add(player)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun openGameView() {
        val gameViewModel: GameViewModel by viewModels()
        val intent = Intent(this, GameFragment::class.java)
        val players = listOf(Player("1", "Mathias", true), Player("2", "Bengt", true))
        val docRef = gameViewModel.createGame("halla", 3, players).observe(this) { gameRef ->
            intent.putExtra("GAME_REFERENCE", gameRef)
            startActivity(intent)
        }
    }
}