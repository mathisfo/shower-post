package com.progark.gameofwits.models

import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Round
import org.junit.Assert.assertEquals
import org.junit.Test

class GameTest {

    @Test
    fun testCorrectScore() {
        val rounds = listOf(
            Round("dacr", mapOf("user1" to "car", "user2" to "card")),
            Round("enif", mapOf("user1" to "fin", "user2" to "fine")))
        val game = Game("id123", rounds,2,2, mutableMapOf(),true)
        game.calculateScore()
        assertEquals(game.scores["user1"], 6)
        assertEquals(game.scores["user2"], 8)
        assertEquals(game.scores["user3"], null)

        val best = game.getBestScore()
        assertEquals(best.first, "user2")
        assertEquals(best.second, 8)
    }

    @Test
    fun testSkip() {
        val rounds = listOf(
            Round("dacr", mapOf("user1" to " ")),
            Round("enif", mapOf("user1" to "fin")))
        val game = Game("id123", rounds,2,2, mutableMapOf(),true)
        game.calculateScore()
        assertEquals(game.scores["user1"], 3)
    }

}