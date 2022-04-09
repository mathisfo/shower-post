package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.progark.gameofwits.model.Player
import com.progark.gameofwits.viewmodel.GameViewModel
import com.google.firebase.components.Dependency
import com.progark.gameofwits.controller.LobbyController
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.viewmodel.LobbyViewModel

class LobbyView() : AppCompatActivity() {

    val lobbyviewmodel: LobbyViewModel by viewModels();




      override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.lobbyview)
        val i: Intent = getIntent();
        val hoster: String = i.getExtras()!!.getString("lobbyHoster", "")
        val btn: Button = findViewById(R.id.nextbutton)
        val checkbox1: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox1)
        val checkbox2: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox2)
        val hostCheckbox: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox1)
          val activeLobby = lobbyviewmodel.getLobby().observe(this) { lobby ->

              println(lobby)
              hostCheckbox.setText(lobby.hostName)
          }
        btn.setOnClickListener { openGameView() }
        //val pin = CreateGameFragment().getGamePIN()
        //println("PIN: " + pin)
    }



    private fun openGameView() {
        val gameViewModel: GameViewModel by viewModels()
        val intent = Intent(this, GameView::class.java)
        val players = listOf(Player("1", "Mathias", true), Player("2", "Bengt", true))
        val docRef = gameViewModel.createGame("halla", 3, players).observe(this) {gameRef ->
            intent.putExtra("GAME_REFERENCE", gameRef)
            startActivity(intent)
        }
    }
}