package com.progark.gameofwits

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)

        val endofgame: Button = findViewById(R.id.endofgamebtn)

        endofgame.setOnClickListener { openEndOfGameView() }
        val textInput: TextInputEditText = findViewById(R.id.textInputEditText)
        val confirmWordBtn: Button = findViewById(R.id.confirmWordButton)
        confirmWordBtn.setOnClickListener {sendWordToFirebase(textInput.text.toString())}
    }

    private fun sendWordToFirebase(word: String) {
        val db = Firebase.firestore
        val docRef = db.collection("games").document("game1")
        val wordEntry = hashMapOf(
            // TODO: fetch the username and use that instead
            "user2" to word
        )

        // TODO: Dynamically set the round-number
        docRef.update("round1", FieldValue.arrayUnion(wordEntry)).addOnSuccessListener{ documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding word", e)
            }
    }

    private fun openEndOfGameView() {
        val intent = Intent(this, EndOfGameView::class.java)
        startActivity(intent)
    }
}