package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.components.Dependency
import com.progark.gameofwits.controller.LobbyController
import storage.Repository

class LobbyView(dependency: Dependency) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobbyview)
        val btn: Button = findViewById(R.id.nextbutton)
        btn.setOnClickListener { openGameView() }
    }

    private fun openGameView() {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }
}