package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class LobbyView() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobbyview)
        val btn: Button = findViewById(R.id.nextbutton)
        val checkbox1: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox1)
        val checkbox2: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox2)
        btn.setOnClickListener { openGameView() }
        //val pin = CreateGameFragment().getGamePIN()
        //println("PIN: " + pin)
    }



    private fun openGameView() {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }
}