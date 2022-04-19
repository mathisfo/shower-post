package com.progark.gameofwits.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.progark.gameofwits.MainActivity
import com.progark.gameofwits.R

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
        val intent = Intent(this, LobbyFragment::class.java)
        startActivity(intent)
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}