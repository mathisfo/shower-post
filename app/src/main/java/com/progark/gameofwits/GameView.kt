package com.progark.gameofwits

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.storage.documents.GameDoc
import com.progark.gameofwits.viewmodel.GameViewModel

class GameView : AppCompatActivity() {
    private var word: String = ""
    private var buttons: List<Button> = listOf()
    private val gameViewModel: GameViewModel by viewModels()
    private var gameID: String = ""
    private var user: String = "2"
    private var turn: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)
        gameID = intent.getStringExtra("GAME_REFERENCE")!!
        gameViewModel.getGame(gameID).observe(this){game ->
            turn = game.currentTurn!!
            setLetters(game)
        }
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
        buttons = listOf(letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9, letter10)
        val writtenWord: TextView = findViewById(R.id.word)
        writtenWord.text = word
        buttons.forEach { btn -> btn.setOnClickListener { letterClicked(btn) }}
        endofgame.setOnClickListener { openEndOfGameView() }
    }

    private fun setLetters(game: GameDoc) {
        var letters: List<String> = game.letterArrays!!.turn1
        if (game.currentTurn == 2) letters = game.letterArrays!!.turn2!!
        if (game.currentTurn == 3) letters = game.letterArrays!!.turn3!!
        if (game.currentTurn == 4) letters = game.letterArrays!!.turn4!!
        if (game.currentTurn == 5) letters = game.letterArrays!!.turn5!!
        for(i in 0..9) {
            buttons[i].text = letters[i]
        }
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
        gameViewModel.enterWord(user, word, turn, gameID).observe(this) {
            word -> openIntermissionView()
        }
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