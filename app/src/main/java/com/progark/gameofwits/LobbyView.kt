package com.progark.gameofwits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.components.Dependency
import com.progark.gameofwits.controller.LobbyController
import storage.Repository

class LobbyView() : AppCompatActivity() {
    val controller = LobbyController();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobbyview)
        val btn: Button = findViewById(R.id.nextbutton)
        val checkbox1: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox1)
        val checkbox2: CheckBox = findViewById<CheckBox>(R.id.playerCheckbox2)
        checkbox1.setText(controller.lobbyMembers("1").get("1"))
        checkbox2.setText(controller.lobbyMembers("1").get("2"))

        btn.setOnClickListener { openGameView() }
    }

    private fun openGameView() {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }
}