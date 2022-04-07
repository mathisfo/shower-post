package com.progark.gameofwits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IntermissionView : AppCompatActivity() {
    private var turn: Number = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intermissionview)

        val nextword: Button = findViewById(R.id.nextword)
        nextword.setOnClickListener { goToGameView(turn) }
    }

    private fun goToGameView(turn: Number) {
        val intent = Intent(this, GameView::class.java)
        startActivity(intent)
    }
}