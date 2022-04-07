package com.progark.gameofwits

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.stream.IntStream.range

class GameView : AppCompatActivity() {
    private var word: String = ""
    private var buttons: Array<Button> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)


        val gameLetters: Array<String> = arrayOf<String>("A", "B", "C", "D", "E", "F", "G", "H", "I", "J") // fetchLetters()
        val endofgame: Button = findViewById(R.id.endofgamebtn)
        val enterword: Button = findViewById(R.id.enterword)
        enterword.setOnClickListener { enterWordHandler() }
        val resetbtn: Button = findViewById(R.id.reset)
        resetbtn.setOnClickListener { resetWord() }
        val letter1: Button = findViewById(R.id.letter1)
        val letter2: Button = findViewById(R.id.letter2)
        val letter3: Button = findViewById(R.id.letter3)
        val letter4: Button = findViewById(R.id.letter4)
        val letter5: Button = findViewById(R.id.letter5)
        val letter6: Button = findViewById(R.id.letter6)
        val letter7: Button = findViewById(R.id.letter7)
        val letter8: Button = findViewById(R.id.letter8)
        val letter9: Button = findViewById(R.id.letter9)
        val letter10: Button = findViewById(R.id.letter10)
        buttons = arrayOf(letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9, letter10)
        for(i in 0..9) {
            buttons[i].text = gameLetters[i]
        }
        val writtenWord: TextView = findViewById(R.id.word)
        writtenWord.text = word
        buttons.forEach { btn -> btn.setOnClickListener { letterClicked(btn) }}
        endofgame.setOnClickListener { openEndOfGameView() }
    }

    private fun letterClicked(button: Button) {
        word += button.getText().toString()
        button.isEnabled = false
        button.isClickable = false
        val written: TextView = findViewById(R.id.word)
        written.text = word
    }

    //TODO: Add functionality for sending word and switching to intermission view
    private fun enterWordHandler() {
        //TODO: store word somewhere
        openIntermissionView()
    }

    private fun resetWord() {
        word = ""
        val written: TextView = findViewById(R.id.word)
        written.text = word
        buttons.forEach { btn ->
            btn.isEnabled = true
            btn.isClickable = true
         }
    }

    //private fun fetchLetters(): Array<String> {
    //    val db = Firebase.firestore
    //    return db.collection("letters").document("letters").get().result.data?.get("letters") as Array<String>
    //}

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

    private fun openIntermissionView() {
        val intent = Intent(this, IntermissionView::class.java)
        startActivity(intent)
    }

    private fun openEndOfGameView() {
        val intent = Intent(this, EndOfGameView::class.java)
        startActivity(intent)
    }
}