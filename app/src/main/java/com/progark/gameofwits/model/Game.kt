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
    val rounds: MutableList<Round> = mutableListOf(),
    // val settings : Settings TODO: Create settings based on lobby?
    val current_round: Int,
    val max_round: Int,
    val scores: Map<String, Int>
) {
    fun startNextRound() {
        val letters = createRandomLetters()
        rounds.last().finished = true
        rounds.add(Round("asf", letters))
    }
}