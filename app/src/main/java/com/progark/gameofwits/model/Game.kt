package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName
import model.User


fun createRandomLetters(): String {
    return "abcdfaa"
}

data class Game(
    val id: String,
    val Letters: Map<Char, Int> = HashMap(),
    val rounds: MutableList<Round> = mutableListOf(),
    val players: List<User> = listOf()
) {
    fun startNextRound() {
        val letters = createRandomLetters()
        rounds.last().finished = true
        rounds.add(Round(letters))
    }
}