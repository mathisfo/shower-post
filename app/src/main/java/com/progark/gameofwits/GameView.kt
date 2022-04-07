package com.progark.gameofwits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)

        fetchLetters()
        val btn: Button = findViewById(R.id.backButton)
        val endofgame: Button = findViewById(R.id.endofgamebtn)

        btn.setOnClickListener { openLobbyView() }
        endofgame.setOnClickListener { openEndOfGameView() }
    }

    private fun fetchLetters() {
        val db = Firebase.firestore

        val letters = db.collection("letters").document("letters").get().addOnSuccessListener { letterArray ->
            println(letterArray.data)
        }

    }
    private fun openLobbyView() {
        val intent = Intent(this, LobbyView::class.java)
        startActivity(intent)
    }
    private fun openEndOfGameView() {
        val intent = Intent(this, EndOfGameView::class.java)
        startActivity(intent)
    }
}