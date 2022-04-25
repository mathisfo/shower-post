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
                val score = if(round.answers[user] == " ") 0 else round.answers[user]?.length ?: 0
                scores[user] = scores[user]!! + score
            }
        }
    }

    fun getBestScore(): Pair<String, Int> {
        val score = scores.entries.reduce { max, entry->
            if (entry.value > max.value) {
                entry
            } else {
                max
            }
        }
        return score.toPair()
    }
}