package com.progark.gameofwits

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.model.Lobby

class GameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameview)

        val btn: Button = findViewById(R.id.backButton)
        btn.setOnClickListener { openLobbyView() }

        val loadLobbyBtn: Button = findViewById(R.id.loadLobbyButton)
        loadLobbyBtn.setOnClickListener {loadLobby()}

        val textInput: TextInputEditText = findViewById(R.id.textInputEditText)
        val confirmWordBtn: Button = findViewById(R.id.confirmWordButton)
        confirmWordBtn.setOnClickListener {sendWordToFirebase(textInput.text.toString())}
    }

    private fun loadLobby() : Lobby? {
        val db = Firebase.firestore
        var lobby: Lobby? = null
        db.collection("lobbies").document("D82qYqJLp1oyjJ8EP3cC").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document}")
                lobby = Lobby("D82qYqJLp1oyjJ8EP3cC", true, document.get("users") as List<String>)
                println(lobby)
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        updateText(lobby)
        return lobby
    }

    private fun updateText (lobby: Lobby?) {
        val outputID: TextView = findViewById<TextView>(R.id.outputID)
        if (lobby?.id === "D82qYqJLp1oyjJ8EP3cC"){
            outputID.text = lobby.id
        } else {
            outputID.text = "Did not work"
        }
    }
    private fun sendWordToFirebase(word: String) {
        val db = Firebase.firestore

        db.collection("words").add(word)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun openLobbyView() {
        val intent = Intent(this, LobbyView::class.java)
        startActivity(intent)
    }
}