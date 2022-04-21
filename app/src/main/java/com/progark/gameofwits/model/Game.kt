package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName
import model.User

fun createRandomLetters(): String = List(10) {
    (('A'..'Z')+('A')+('E')+('I')+('O')+('U')+('Y')+('A')+('E')+('I')).random()
}.joinToString("")

data class Game(
    val id: String,
    val rounds: List<Round> = listOf(),
    // val settings : Settings TODO: Create settings based on lobby?
    val current_round: Int,
    val max_round: Int,
    val scores: Map<String, Int>
) {
    fun startNextRound() {
        val letters = createRandomLetters()
        rounds.last().finished = true
    }
}