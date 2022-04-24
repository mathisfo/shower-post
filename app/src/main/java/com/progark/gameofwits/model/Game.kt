package com.progark.gameofwits.model


fun createRandomLetters(): String = List(15) {
    (('A'..'Z') + ('A') + ('E') + ('I') + ('O') + ('U') + ('Y') + ('A') + ('E') + ('I')).random()
}.joinToString("")

data class Game(
    val id: String,
    val rounds: List<Round> = listOf(),
    // val settings : Settings TODO: Create settings based on lobby?
    var current_round: Int,
    val max_round: Int,
    val scores: MutableMap<String, Int>,
    val ended: Boolean
) {
    fun calculateScore() {
        for (round in rounds) {
            round.answers.keys.forEach { user ->
                if (scores[user] == null) scores[user] = 0
                scores[user] = scores[user]!! + (round.answers[user]?.length ?: 0)
            }
        }
    }
}