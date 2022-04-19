package com.progark.gameofwits.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.progark.gameofwits.R

class IntermissionView : AppCompatActivity() {
    private var turn: Number = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intermissionview)

        val nextword: Button = findViewById(R.id.nextword)
        nextword.setOnClickListener { goToGameView(turn) }
    }

    private fun goToGameView(turn: Number) {
        val intent = Intent(this, GameRoundFragment::class.java)
        startActivity(intent)
    }
}