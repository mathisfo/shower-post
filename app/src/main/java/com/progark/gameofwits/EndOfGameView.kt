package com.progark.gameofwits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EndOfGameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.endofgameview)

        val playagain: Button = findViewById(R.id.playagainbtn)
        val mainmenu: Button = findViewById(R.id.mainmenubtn)

        playagain.setOnClickListener { openLobbyView() }
        mainmenu.setOnClickListener { openMainActivity() }
    }

    private fun openLobbyView() {
        val intent = Intent(this, LobbyView::class.java)
        startActivity(intent)
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}