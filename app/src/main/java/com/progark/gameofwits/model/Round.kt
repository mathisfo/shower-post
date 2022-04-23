package com.progark.gameofwits.model

data class Round(val letters: String, val answers: Map<String, String>) {
    val availableLetter: MutableMap<Char, Int> = HashMap()
    var finished: Boolean = false

    init {
        // Adds the count up once for each letter
        for (c in letters) {
            availableLetter[c] = (availableLetter[c] ?: 0) + 1
        }
    }

    fun isValidLetters(word: String): Boolean {
        val counter = HashMap(availableLetter)
        for (c in word) {
            val value = counter[c] ?: return false
            if (value - 1 < 0) return false
            counter[c] = counter[c]!! - 1
        }
        return true
    }
}