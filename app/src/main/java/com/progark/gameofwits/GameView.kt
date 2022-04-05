package com.progark.gameofwits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)
        val btn: Button = findViewById(R.id.backButton)

        btn.setOnClickListener { openLobbyView() }
    }

    private fun openLobbyView() {
        val intent = Intent(this, LobbyView::class.java)
        startActivity(intent)
    }
}