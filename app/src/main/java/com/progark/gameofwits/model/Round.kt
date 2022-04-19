package com.progark.gameofwits.model

import model.User

data class Round(val id: String, private val letters: String) {
    val availableLetter: MutableMap<Char, Int> = HashMap()
    var finished: Boolean = false
    private val submitted = HashMap<User, String>()

    init {
        // Adds the count up once for each letter
        for (c in letters) {
            availableLetter[c] = (availableLetter[c] ?: 0) + 1
        }
    }

    fun submitWord(word: String, user: User) {
        val valid = isValidWord(word)
        // Throw error perhaps
        if (!valid) return
        // Throw error, user has submitted
        submitted[user] = word
    }

    private fun isValidWord(word: String): Boolean {
        val counter = HashMap(availableLetter)
        for (c in word) {
            val value = counter[c] ?: return false
            if (value - 1 < 0) return false
            counter[c] = counter[c]!! - 1
        }
        return true
    }
}